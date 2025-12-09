package com.localgo.artelabspa.data.remote.api

import com.localgo.artelabspa.data.remote.RetrofitClient
import com.localgo.artelabspa.data.remote.dto.UpdateProfileRequest
import okhttp3.MultipartBody
import retrofit2.http.*

interface AvatarApiService {

    @Multipart
    @POST("upload/image")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): UploadResponse

    @PUT("cliente-profile/me")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: UpdateProfileRequest
    ): UpdateProfileResponse

    companion object {
        fun create(): AvatarApiService {
            return RetrofitClient.createAvatarApiService()
        }
    }
}

data class UploadResponse(
    val success: Boolean,
    val imageUrl: String
)

data class UpdateProfileResponse(
    val success: Boolean,
    val message: String
)
