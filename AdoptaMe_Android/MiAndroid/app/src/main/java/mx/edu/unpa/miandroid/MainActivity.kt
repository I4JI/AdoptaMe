package mx.edu.unpa.miandroid

import android.content.Intent
import android.content.SharedPreferences
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
import mx.edu.unpa.miandroid.model.AuthResponse
import mx.edu.unpa.miandroid.model.LoginRequest
import mx.edu.unpa.miandroid.utils.Validaciones
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        val idUsuario = prefs.getLong("idUsuario", -1L)

        if (idUsuario != -1L) {
            startActivity(Intent(this, Dashboard::class.java))
            finish()
            return
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun login(view: View) {
        val email = findViewById<EditText>(R.id.txtEmail).text.toString().trim()
        val password = findViewById<EditText>(R.id.txtPassword).text.toString()

        if (!Validaciones.noVacio(email) || !Validaciones.noVacio(password)) {
            Toast.makeText(this, "Completa ambos campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (!Validaciones.esGmail(email)) {
            Toast.makeText(this, "Solo se permiten correos Gmail", Toast.LENGTH_SHORT).show()
            return
        }

        val request = LoginRequest(email = email, password = password)

        RetrofitClient.instance.login(request).enqueue(object : retrofit2.Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val auth = response.body()
                    if (auth != null) {
                        prefs.edit()
                            .putLong("idUsuario", auth.idUsuario)
                            .putString("nombreUsuario", auth.nombre)
                            .putString("emailUsuario", auth.email)
                            .apply()

                        startActivity(Intent(this@MainActivity, Dashboard::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Correo o contraseña incorrectos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("LOGIN", "Fallo", t)
            }
        })
    }

    fun create(view: View) {
        startActivity(Intent(this, Account::class.java))
    }
}