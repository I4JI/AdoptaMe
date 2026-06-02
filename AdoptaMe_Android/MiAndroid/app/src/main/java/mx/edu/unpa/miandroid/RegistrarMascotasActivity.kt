package mx.edu.unpa.miandroid

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import mx.edu.unpa.miandroid.client.RetrofitClient
import mx.edu.unpa.miandroid.model.Mascota
import mx.edu.unpa.miandroid.model.MascotaRequest
import mx.edu.unpa.miandroid.model.TipoMascota
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class RegistrarMascotaActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private var tiposMascota: List<TipoMascota> = emptyList()
    private lateinit var spinnerTipos: Spinner
    private lateinit var imgPreview: ImageView

    private var imagenSeleccionadaUri: Uri? = null
    private var bitmapCamara: Bitmap? = null

    private val selectorImagen =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                imagenSeleccionadaUri = uri
                bitmapCamara = null
                Glide.with(this)
                    .load(uri)
                    .into(imgPreview)
            }
        }

    private val tomarFotoLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                bitmapCamara = bitmap
                imagenSeleccionadaUri = null
                imgPreview.setImageBitmap(bitmap)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar_mascota)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        spinnerTipos = findViewById(R.id.spinnerTipoMascota)
        imgPreview = findViewById(R.id.imgPreviewMascota)

        findViewById<android.widget.Button>(R.id.btnElegirImagen).setOnClickListener {
            selectorImagen.launch("image/*")
        }

        findViewById<android.widget.Button>(R.id.btnTomarFoto).setOnClickListener {
            tomarFotoLauncher.launch(null)
        }

        cargarTipos()
    }

    private fun cargarTipos() {
        RetrofitClient.instance.getTiposMascota()
            .enqueue(object : retrofit2.Callback<List<TipoMascota>> {
                override fun onResponse(
                    call: Call<List<TipoMascota>>,
                    response: Response<List<TipoMascota>>
                ) {
                    if (response.isSuccessful) {
                        tiposMascota = response.body() ?: emptyList()
                        val nombres = tiposMascota.map { it.descripcion }

                        val adapter = ArrayAdapter(
                            this@RegistrarMascotaActivity,
                            android.R.layout.simple_spinner_dropdown_item,
                            nombres
                        )
                        spinnerTipos.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<List<TipoMascota>>, t: Throwable) {
                    Toast.makeText(
                        this@RegistrarMascotaActivity,
                        "Error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    fun guardarMascota(view: android.view.View) {
        val idUsuario = prefs.getLong("idUsuario", -1L)
        if (idUsuario == -1L) {
            Toast.makeText(this, "Sesión inválida", Toast.LENGTH_SHORT).show()
            return
        }

        val nombre = findViewById<EditText>(R.id.txtNombreMascota).text.toString().trim()
        val raza = findViewById<EditText>(R.id.txtRazaMascota).text.toString().trim()
        val sexo = findViewById<EditText>(R.id.txtSexoMascota).text.toString().trim()
        val edad = findViewById<EditText>(R.id.txtEdadMascota).text.toString().trim()
        val descripcion = findViewById<EditText>(R.id.txtDescripcionMascota).text.toString().trim()

        if (nombre.isBlank() || sexo.isBlank()) {
            Toast.makeText(this, "Nombre y sexo son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val tipoSeleccionado = tiposMascota.getOrNull(spinnerTipos.selectedItemPosition)
        if (tipoSeleccionado == null) {
            Toast.makeText(this, "Selecciona un tipo de mascota", Toast.LENGTH_SHORT).show()
            return
        }

        val request = MascotaRequest(
            idUsuarioDonador = idUsuario,
            idTipoMascota = tipoSeleccionado.idTipoMascota,
            nombre = nombre,
            raza = if (raza.isBlank()) null else raza,
            sexo = sexo,
            edadAproximada = if (edad.isBlank()) null else edad,
            descripcion = if (descripcion.isBlank()) null else descripcion,
            estadoAdopcion = "Disponible"
        )

        RetrofitClient.instance.crearMascota(request)
            .enqueue(object : retrofit2.Callback<Mascota> {
                override fun onResponse(call: Call<Mascota>, response: Response<Mascota>) {
                    if (response.isSuccessful) {
                        val mascotaCreada = response.body()
                        if (mascotaCreada == null) {
                            Toast.makeText(this@RegistrarMascotaActivity, "No se pudo crear la mascota", Toast.LENGTH_SHORT).show()
                            return
                        }

                        when {
                            imagenSeleccionadaUri != null -> {
                                subirImagenDesdeUri(mascotaCreada.idMascota, imagenSeleccionadaUri!!)
                            }
                            bitmapCamara != null -> {
                                subirImagenDesdeBitmap(mascotaCreada.idMascota, bitmapCamara!!)
                            }
                            else -> {
                                Toast.makeText(
                                    this@RegistrarMascotaActivity,
                                    "Mascota registrada sin imagen",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            }
                        }
                    } else {
                        Toast.makeText(this@RegistrarMascotaActivity, "No se pudo registrar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Mascota>, t: Throwable) {
                    Toast.makeText(this@RegistrarMascotaActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun subirImagenDesdeUri(idMascota: Long, uri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            if (inputStream == null) {
                Toast.makeText(this, "No se pudo leer la imagen", Toast.LENGTH_SHORT).show()
                return
            }

            val tempFile = File.createTempFile("mascota_uri_", ".jpg", cacheDir)
            val outputStream = FileOutputStream(tempFile)

            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()

            subirArchivoTemporal(idMascota, tempFile)
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun subirImagenDesdeBitmap(idMascota: Long, bitmap: Bitmap) {
        try {
            val tempFile = File.createTempFile("mascota_camara_", ".jpg", cacheDir)
            val outputStream = FileOutputStream(tempFile)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            outputStream.flush()
            outputStream.close()

            subirArchivoTemporal(idMascota, tempFile)
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun subirArchivoTemporal(idMascota: Long, file: File) {
        val requestFile = file.asRequestBody("image/*".toMediaType())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        RetrofitClient.instance.uploadMascotaImage(idMascota, body, true)
            .enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@RegistrarMascotaActivity,
                            "Mascota e imagen guardadas",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@RegistrarMascotaActivity,
                            "Mascota guardada, pero falló la imagen (${response.code()})",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    finish()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(
                        this@RegistrarMascotaActivity,
                        "Mascota guardada, pero falló la imagen",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            })
    }
}