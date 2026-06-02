package mx.edu.unpa.miandroid.service

import mx.edu.unpa.miandroid.model.AuthResponse
import mx.edu.unpa.miandroid.model.LoginRequest
import mx.edu.unpa.miandroid.model.Mascota
import mx.edu.unpa.miandroid.model.MascotaRequest
import mx.edu.unpa.miandroid.model.TipoMascota
import mx.edu.unpa.miandroid.model.UsuarioRequest
import mx.edu.unpa.miandroid.model.UsuarioResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<AuthResponse>

    @POST("api/auth/register")
    fun register(@Body request: UsuarioRequest): Call<UsuarioResponse>

    @GET("api/tipos-mascota")
    fun getTiposMascota(): Call<List<TipoMascota>>

    @GET("api/mascotas/tipo/{idTipoMascota}")
    fun getMascotasByTipo(@Path("idTipoMascota") idTipoMascota: Long): Call<List<Mascota>>

    @GET("api/mascotas/{idMascota}")
    fun getMascotaById(@Path("idMascota") idMascota: Long): Call<Mascota>

    @POST("api/mascotas")
    fun crearMascota(@Body request: MascotaRequest): Call<Mascota>

    @Multipart
    @POST("api/imagenes-mascota/upload/{idMascota}")
    fun uploadMascotaImage(
        @Path("idMascota") idMascota: Long,
        @Part file: MultipartBody.Part,
        @Query("principal") principal: Boolean = false
    ): Call<String>
}