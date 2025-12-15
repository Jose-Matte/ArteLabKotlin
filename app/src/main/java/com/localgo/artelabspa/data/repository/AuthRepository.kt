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

    suspend fun login(email: String, password: String): LoginResponse {
        return api.login(LoginRequest(email, password))
    }

    suspend fun register(
        nombre: String,
        email: String,
        password: String,
        role: String
    ): RegisterResponse {
        return api.register(
            RegisterRequest(
                email = email,
                password = password,
                role = role,
                nombre = nombre
            )
        )
    }

    fun saveUserData(id: String?, email: String?, role: String?) {
        sessionManager.saveUserId(id)
        sessionManager.saveEmail(email)
        sessionManager.saveRole(role)
    }
}

