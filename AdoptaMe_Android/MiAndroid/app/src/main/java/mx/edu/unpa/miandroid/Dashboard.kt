package mx.edu.unpa.miandroid

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar

class Dashboard : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        prefs = getSharedPreferences("sesion", MODE_PRIVATE)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarDashboard)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val nombre = prefs.getString("nombreUsuario", "Usuario")
        findViewById<TextView>(R.id.txtBienvenido).text = "Hola, $nombre"

        findViewById<CardView>(R.id.cardPerro).setOnClickListener {
            abrirListaMascotas(1L, "Perro")
        }
        findViewById<CardView>(R.id.cardGato).setOnClickListener {
            abrirListaMascotas(2L, "Gato")
        }
        findViewById<CardView>(R.id.cardLoro).setOnClickListener {
            abrirListaMascotas(3L, "Loro")
        }
        findViewById<CardView>(R.id.cardHamster).setOnClickListener {
            abrirListaMascotas(4L, "Hamster")
        }

        findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.btnAgregarMascota)
            .setOnClickListener {
                startActivity(Intent(this, RegistrarMascotaActivity::class.java))
            }
    }

    private fun abrirListaMascotas(idTipo: Long, nombreTipo: String) {
        val intent = Intent(this, ListaMascotasActivity::class.java)
        intent.putExtra("idTipoMascota", idTipo)
        intent.putExtra("nombreTipoMascota", nombreTipo)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_config -> {
                Toast.makeText(this, "Configuración", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_logout -> {
                prefs.edit().clear().apply()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}