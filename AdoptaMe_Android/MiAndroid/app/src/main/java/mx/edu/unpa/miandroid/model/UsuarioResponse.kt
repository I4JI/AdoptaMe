package mx.edu.unpa.miandroid.model

import java.util.Date

data class UsuarioResponse(
    val id: Long,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String? = null,
    val email: String,
    val telefono: String? = null,
    val activo: Boolean? = true,
    val fechaRegistro: String? = null
)