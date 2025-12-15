package com.localgo.artelabspa.data.repository

import android.content.Context
import android.net.Uri
import com.localgo.artelabspa.data.local.SessionManager
import com.localgo.artelabspa.data.remote.api.ClienteProfileApiService
import com.localgo.artelabspa.data.remote.dto.ClienteProfileRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class ClienteProfileRepository(
    private val api: ClienteProfileApiService,
    private val session: SessionManager,
    private val context: Context
) {

    suspend fun loadMyProfile() = api.getMyProfile("Bearer ${session.getToken()}")

    suspend fun updateProfile(data: ClienteProfileRequest) =
        api.updateMyProfile("Bearer ${session.getToken()}", data)

    suspend fun uploadAvatar(uri: Uri): String? {
        val input = context.contentResolver.openInputStream(uri) ?: return null
        val file = File.createTempFile("avatar_", ".jpg", context.cacheDir)
        FileOutputStream(file).use { input.copyTo(it) }

        val body = MultipartBody.Part.createFormData(
            "avatar",
            file.name,
            file.asRequestBody()
        )

        val resp = api.uploadAvatar("Bearer ${session.getToken()}", body)

        // Guardar en session
        session.saveAvatar(resp.avatarUrl ?: "")

        return resp.avatarUrl
    }
}
