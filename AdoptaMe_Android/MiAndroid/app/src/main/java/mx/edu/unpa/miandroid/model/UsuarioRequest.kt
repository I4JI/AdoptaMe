package mx.edu.unpa.miandroid.model

data class UsuarioRequest(
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String? = null,
    val email: String,
    val telefono: String? = null,
    val password: String
)