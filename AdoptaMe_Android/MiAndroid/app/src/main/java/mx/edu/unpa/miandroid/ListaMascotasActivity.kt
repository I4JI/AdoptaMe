package mx.edu.unpa.miandroid

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.edu.unpa.miandroid.adapter.MascotaAdapter
import mx.edu.unpa.miandroid.client.RetrofitClient
import mx.edu.unpa.miandroid.model.Mascota
import retrofit2.Call
import retrofit2.Response

class ListaMascotasActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: MascotaAdapter
    private var listaMascotas: List<Mascota> = emptyList()
    private var idTipoMascota: Long = 0L
    private var nombreTipoMascota: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_mascotas)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        idTipoMascota = intent.getLongExtra("idTipoMascota", 0L)
        nombreTipoMascota = intent.getStringExtra("nombreTipoMascota") ?: ""

        findViewById<TextView>(R.id.txtTituloLista).text = nombreTipoMascota

        recycler = findViewById(R.id.recyclerMascotas)
        adapter = MascotaAdapter(emptyList()) { mascota ->
            val intent = Intent(this, DetalleMascotaActivity::class.java)
            intent.putExtra("idMascota", mascota.idMascota)
            startActivity(intent)
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        cargarMascotas()
    }

    private fun cargarMascotas() {
        RetrofitClient.instance.getMascotasByTipo(idTipoMascota)
            .enqueue(object : retrofit2.Callback<List<Mascota>> {
                override fun onResponse(
                    call: Call<List<Mascota>>,
                    response: Response<List<Mascota>>
                ) {
                    if (response.isSuccessful) {
                        listaMascotas = response.body() ?: emptyList()
                        adapter.actualizarLista(listaMascotas)
                    } else {
                        Toast.makeText(this@ListaMascotasActivity, "No se pudieron cargar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Mascota>>, t: Throwable) {
                    Toast.makeText(this@ListaMascotasActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}