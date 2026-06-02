package mx.edu.unpa.miandroid.model

data class Mascota(
    val idMascota: Long,
    val idUsuarioDonador: Long,
    val idTipoMascota: Long,
    val tipoMascota: String? = null,
    val nombre: String,
    val raza: String? = null,
    val sexo: String,
    val edadAproximada: String? = null,
    val descripcion: String? = null,
    val estadoAdopcion: String? = null,
    val activo: Boolean? = true,
    val fechaPublicacion: String? = null,
    val imagenes: List<String>? = emptyList()
)