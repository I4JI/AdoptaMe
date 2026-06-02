package mx.edu.unpa.miandroid

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mx.edu.unpa.miandroid.adapter.ImagenMascotaAdapter
import mx.edu.unpa.miandroid.client.RetrofitClient
import mx.edu.unpa.miandroid.model.Mascota
import retrofit2.Call
import retrofit2.Response

class DetalleMascotaActivity : AppCompatActivity() {

    private lateinit var recyclerImagenes: RecyclerView
    private lateinit var adapterImagenes: ImagenMascotaAdapter
    private lateinit var imgPrincipal: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_mascota)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imgPrincipal = findViewById(R.id.imgPrincipalMascota)

        recyclerImagenes = findViewById(R.id.recyclerImagenesMascota)
        adapterImagenes = ImagenMascotaAdapter(emptyList())
        recyclerImagenes.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerImagenes.adapter = adapterImagenes

        val idMascota = intent.getLongExtra("idMascota", 0L)
        cargarDetalle(idMascota)
    }

    private fun cargarDetalle(idMascota: Long) {
        RetrofitClient.instance.getMascotaById(idMascota)
            .enqueue(object : retrofit2.Callback<Mascota> {
                override fun onResponse(call: Call<Mascota>, response: Response<Mascota>) {
                    if (response.isSuccessful) {
                        val mascota = response.body()
                        if (mascota != null) {
                            findViewById<TextView>(R.id.txtNombreDetalle).text = mascota.nombre
                            findViewById<TextView>(R.id.txtRazaDetalle).text = mascota.raza ?: "Sin raza"
                            findViewById<TextView>(R.id.txtSexoDetalle).text = mascota.sexo
                            findViewById<TextView>(R.id.txtEdadDetalle).text = mascota.edadAproximada ?: "Sin dato"
                            findViewById<TextView>(R.id.txtDescripcionDetalle).text =
                                mascota.descripcion ?: "Sin descripción"

                            val imagenes = mascota.imagenes ?: emptyList()
                            adapterImagenes.actualizarLista(imagenes)

                            val primeraImagen = imagenes.firstOrNull()
                            if (!primeraImagen.isNullOrBlank()) {
                                val urlCompleta = if (primeraImagen.startsWith("http")) {
                                    primeraImagen
                                } else {
                                    RetrofitClient.BASE_URL.trimEnd('/') + "/" + primeraImagen.trimStart('/')
                                }

                                Glide.with(this@DetalleMascotaActivity)
                                    .load(urlCompleta)
                                    .placeholder(R.drawable.ic_logo_adoptame_foreground)
                                    .into(imgPrincipal)
                            } else {
                                imgPrincipal.setImageResource(R.drawable.ic_logo_adoptame_foreground)
                            }
                        }
                    } else {
                        Toast.makeText(this@DetalleMascotaActivity, "No se pudo cargar el detalle", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Mascota>, t: Throwable) {
                    Toast.makeText(this@DetalleMascotaActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}