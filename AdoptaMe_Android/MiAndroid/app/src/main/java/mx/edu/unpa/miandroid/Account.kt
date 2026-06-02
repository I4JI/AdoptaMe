package mx.edu.unpa.miandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import mx.edu.unpa.miandroid.client.RetrofitClient
import mx.edu.unpa.miandroid.model.UsuarioRequest
import mx.edu.unpa.miandroid.model.UsuarioResponse
import mx.edu.unpa.miandroid.utils.Validaciones
import retrofit2.Call
import retrofit2.Response

class Account : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun create(view: View) {
        val nombre = findViewById<EditText>(R.id.txtNombre).text.toString().trim()
        val apellidoPaterno = findViewById<EditText>(R.id.txtApellidoPaterno).text.toString().trim()
        val apellidoMaterno = findViewById<EditText>(R.id.txtApellidoMaterno).text.toString().trim()
        val email = findViewById<EditText>(R.id.txtEmail).text.toString().trim()
        val password = findViewById<EditText>(R.id.txtPassword).text.toString()

        if (!Validaciones.noVacio(nombre) ||
            !Validaciones.noVacio(apellidoPaterno) ||
            !Validaciones.noVacio(email) ||
            !Validaciones.noVacio(password)
        ) {
            Toast.makeText(this, "Completa los campos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        if (!Validaciones.esGmail(email)) {
            Toast.makeText(this, "Solo se permiten correos Gmail", Toast.LENGTH_SHORT).show()
            return
        }

        val request = UsuarioRequest(
            nombre = nombre,
            apellidoPaterno = apellidoPaterno,
            apellidoMaterno = if (apellidoMaterno.isBlank()) null else apellidoMaterno,
            email = email,
            password = password
        )

        RetrofitClient.instance.register(request).enqueue(object : retrofit2.Callback<UsuarioResponse> {
            override fun onResponse(call: Call<UsuarioResponse>, response: Response<UsuarioResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@Account, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Account, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@Account, "No se pudo registrar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UsuarioResponse>, t: Throwable) {
                Toast.makeText(this@Account, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("REGISTER", "Fallo", t)
            }
        })
    }
}