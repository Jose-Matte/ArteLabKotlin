package com.localgo.artelabspa.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.localgo.artelabspa.data.local.SessionManager
import com.localgo.artelabspa.data.remote.api.AvatarApiService
import com.localgo.artelabspa.data.remote.dto.UpdateProfileRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class AvatarRepositoryBackend(
    private val api: AvatarApiService,
    private val sessionManager: SessionManager,
    private val context: Context
) {

    // ---------- SUBIR AVATAR ----------
    suspend fun uploadAvatar(uri: Uri): String? {
        return try {
            val token = sessionManager.getToken() ?: return null

            val tempFile = uriToFile(uri) ?: return null

            val body = MultipartBody.Part.createFormData(
                "image",
                tempFile.name,
                tempFile.asRequestBody()
            )

            // POST → /api/cliente-profile/upload
            val upload = api.uploadImage("Bearer $token", body)

            val avatarUrl = "https://artelabspa-api.onrender.com${upload.imageUrl}"

            // PUT → /api/cliente-profile/me
            api.updateProfile(
                "Bearer $token",
                UpdateProfileRequest(avatarUrl = avatarUrl)
            )

            sessionManager.saveAvatar(avatarUrl)
            avatarUrl
        } catch (e: Exception) {
            Log.e("AVATAR", "ERROR", e)
            null
        }
    }

    // ---------- ACTUALIZAR DATOS ----------
    suspend fun updateProfileData(
        nombre: String?,
        telefono: String?,
        direccion: String?
    ): Boolean {
        return try {
            val token = sessionManager.getToken() ?: return false

            api.updateProfile(
                "Bearer $token",
                UpdateProfileRequest(
                    nombre = nombre,
                    telefono = telefono,
                    direccion = direccion
                )
            )

            true
        } catch (e: Exception) {
            Log.e("PROFILE", "ERROR ACTUALIZANDO PERFIL", e)
            false
        }
    }

    // --------- CONVERTIR URI → FILE ---------
    private fun uriToFile(uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val file = File.createTempFile("avatar_", ".jpg", context.cacheDir)
            FileOutputStream(file).use { output -> inputStream.copyTo(output) }
            file
        } catch (e: Exception) {
            Log.e("AVATAR", "uriToFile ERROR", e)
            null
        }
    }
}
