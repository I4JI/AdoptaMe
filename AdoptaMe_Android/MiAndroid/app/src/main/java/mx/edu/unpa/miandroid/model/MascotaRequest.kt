package mx.edu.unpa.miandroid.model

data class MascotaRequest(
    val idUsuarioDonador: Long,
    val idTipoMascota: Long,
    val nombre: String,
    val raza: String? = null,
    val sexo: String,
    val edadAproximada: String? = null,
    val descripcion: String? = null,
    val estadoAdopcion: String? = "Disponible"
)