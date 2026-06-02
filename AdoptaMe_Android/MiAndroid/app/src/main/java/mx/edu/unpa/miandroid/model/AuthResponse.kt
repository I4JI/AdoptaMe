package mx.edu.unpa.miandroid.model

data class AuthResponse(
    val idUsuario: Long,
    val nombre: String,
    val email: String,
    val mensaje: String
)