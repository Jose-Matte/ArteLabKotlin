package com.localgo.artelabspa.data.repository

import com.localgo.artelabspa.data.remote.api.ApiService
import com.localgo.artelabspa.data.local.SessionManager
import com.localgo.artelabspa.data.remote.dto.LoginRequest
import com.localgo.artelabspa.data.remote.dto.LoginResponse
import com.localgo.artelabspa.data.remote.dto.RegisterRequest
import com.localgo.artelabspa.data.remote.dto.RegisterResponse

class AuthRepository(
    private val api: ApiService,
    private val sessionManager: SessionManager
) {

    // LOGIN
    suspend fun login(email: String, password: String): LoginResponse {
        return api.login(LoginRequest(email, password))
    }

    // REGISTER (CORRECTO)
    suspend fun register(nombre: String, email: String, password: String) =
        api.register(
            RegisterRequest(
                email = email,
                password = password,
                role = "CLIENTE",
                nombre = nombre
            )
        )

    // GUARDAR DATOS DEL USUARIO
    fun saveUserData(id: String?, email: String?, role: String?) {
        sessionManager.saveUserId(id)
        sessionManager.saveEmail(email)
        sessionManager.saveRole(role)
    }
}
