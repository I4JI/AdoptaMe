package mx.edu.unpa.miandroid.model

import java.util.Date

data class Usuario(
    var id:Int?=null,
    var nombre: String,
    var apellidoPaterno: String,
    var apellidoMaterno: String?=null,
    var email: String,
    var telefono: String?=null,
    var password: String?=null,
    var foto: String?=null,
    var activo: Boolean=true,
    var fechaRegistro: String?=null
)
