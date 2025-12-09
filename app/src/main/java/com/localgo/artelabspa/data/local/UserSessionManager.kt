package com.localgo.artelabspa.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserSessionManager(private val context: Context) {

    companion object {
        private val Context.dataStore by preferencesDataStore(name = "user_session")

        private val KEY_TOKEN = stringPreferencesKey("auth_token")
        private val KEY_USER_ID = stringPreferencesKey("user_id")
        private val KEY_EMAIL = stringPreferencesKey("email")
        private val KEY_ROLE = stringPreferencesKey("role")
    }

    // Guardar token
    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = token
        }
    }

    suspend fun getToken(): String? {
        return context.dataStore.data.map { it[KEY_TOKEN] }.first()
    }

    // Guardar datos del user
    suspend fun saveUserData(id: String, email: String, role: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_USER_ID] = id
            prefs[KEY_EMAIL] = email
            prefs[KEY_ROLE] = role
        }
    }

    suspend fun getUserId(): String? = context.dataStore.data.map { it[KEY_USER_ID] }.first()
    suspend fun getUserEmail(): String? = context.dataStore.data.map { it[KEY_EMAIL] }.first()
    suspend fun getUserRole(): String? = context.dataStore.data.map { it[KEY_ROLE] }.first()

    // Logout → borrar todo
    suspend fun clearSession() {
        context.dataStore.edit { it.clear() }
    }
}
