package mx.edu.unpa.miandroid.utils

object Validaciones {
    private val gmailRegex = Regex("^[A-Za-z0-9._%+-]+@gmail\\.com$")

    fun esGmail(email: String): Boolean {
        return gmailRegex.matches(email.trim())
    }

    fun noVacio(texto: String): Boolean {
        return texto.trim().isNotEmpty()
    }
}