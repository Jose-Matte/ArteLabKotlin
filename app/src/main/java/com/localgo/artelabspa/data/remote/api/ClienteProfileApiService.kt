package com.localgo.artelabspa.data.remote.api

import com.localgo.artelabspa.data.remote.dto.ClienteProfileRequest
import com.localgo.artelabspa.data.remote.dto.ClienteProfileResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ClienteProfileApiService {

    // Obtener mis datos
    @GET("cliente-profile/me")
    suspend fun getMyProfile(
        @Header("Authorization") token: String
    ): ClienteProfileResponse

    // Actualizar perfil
    @PUT("cliente-profile/me")
    suspend fun updateMyProfile(
        @Header("Authorization") token: String,
        @Body request: ClienteProfileRequest
    ): ClienteProfileResponse

    // Subir avatar
    @Multipart
    @POST("cliente-profile/upload")
    suspend fun uploadAvatar(
        @Header("Authorization") token: String,
        @Part avatar: MultipartBody.Part
    ): ClienteProfileResponse
}
