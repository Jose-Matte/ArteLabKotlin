package com.localgo.artelabspa.data.remote.api

// --- Importaciones para Login/Register ---
import com.localgo.artelabspa.data.remote.dto.LoginRequest
import com.localgo.artelabspa.data.remote.dto.LoginResponse
import com.localgo.artelabspa.data.remote.dto.RegisterRequest
import com.localgo.artelabspa.data.remote.dto.RegisterResponse

// --- CORRECCIÓN: La ruta correcta a tu modelo es desde 'model' ---
import com.localgo.artelabspa.model.Producto

// --- Importaciones de Retrofit ---
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Interfaz de Retrofit. He unificado tus servicios aquí para simplicidad.
 * En el futuro, podrías tener un ApiService para Auth y otro para Productos.
 */
interface ApiService {

    // --- Métodos de Autenticación ---
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    // --- Método para obtener Productos de tu API ---
    // La respuesta es una lista directa de Productos, lo cual es correcto.
    @GET("Api/Artelab/productos")
    suspend fun getProductos(): List<Producto>
}
