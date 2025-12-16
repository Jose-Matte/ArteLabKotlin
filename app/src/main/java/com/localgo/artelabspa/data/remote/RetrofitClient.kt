package com.localgo.artelabspa.data.remote


import com.localgo.artelabspa.data.remote.api.ApiService
import com.localgo.artelabspa.data.remote.api.AvatarApiService
import com.localgo.artelabspa.data.remote.api.PedidoApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitClient {

    private const val BASE_URL = "https://artelabspa-api.onrender.com/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createApiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    fun createAvatarApiService(): AvatarApiService {
        return retrofit.create(AvatarApiService::class.java)
    }

    fun createPedidoApiService(): PedidoApiService {
        return retrofit.create(PedidoApiService::class.java)
    }


    // --- URL BASE PARA NUESTRA API DE MOCKAPI ---
    private const val BASE_URL_ARTELAB = "https://6931b6a511a8738467d0396a.mockapi.io/"

    // Cliente OkHttp simple, con logging para depurar
    private val artelabOkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    // --- INSTANCIA PRINCIPAL DE RETROFIT PARA NUESTRA APP ---
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_ARTELAB)
            .client(artelabOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

