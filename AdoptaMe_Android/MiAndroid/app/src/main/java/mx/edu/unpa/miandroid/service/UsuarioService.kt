package mx.edu.unpa.miandroid.service


import mx.edu.unpa.miandroid.model.Usuario
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
interface UsuarioService {
    @GET("api/usuarios")
    fun getUsuarios(): Call<List<Usuario>>

    @POST("api/usuarios")
    fun crearUsuario(@Body usuario:Usuario):Call<Usuario>

    @GET("api/usuarios/email/{email}")
    fun getUsuarioByEmail(@Path("email") email: String):Call<Usuario>

    @PUT("api/usuarios/{id}")
    fun actualizarUsuario(@Path("id")id:Int): Call<Void>

    @DELETE("api/usuarios/{id}")
    fun eliminarUsuario(@Path("id")id:Int): Call<Void>

    @Multipart
    @POST("api/upload")
    fun uploadImage(
            @Part file: MultipartBody.Part
    ): Call<String>

}