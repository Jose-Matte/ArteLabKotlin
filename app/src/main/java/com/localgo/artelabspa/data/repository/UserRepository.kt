package com.localgo.artelabspa.data.repository

import com.localgo.artelabspa.data.local.UserSessionManager

class UserRepository(private val sessionManager: UserSessionManager) {

    suspend fun isUserLoggedIn(): Boolean {
        return sessionManager.getToken() != null
    }

    suspend fun logout() {
        sessionManager.clearSession()
    }
}
