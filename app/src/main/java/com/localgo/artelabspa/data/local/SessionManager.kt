package com.localgo.artelabspa.data.local

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("artelab_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_TOKEN = "key_token"
        private const val KEY_EMAIL = "key_email"
        private const val KEY_NAME = "key_name"
        private const val KEY_AVATAR = "key_avatar"
        private const val KEY_USER_ID = "key_user_id"
        private const val KEY_ROLE = "key_role"
    }

    fun saveToken(token: String) = prefs.edit().putString(KEY_TOKEN, token).apply()
    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun saveEmail(email: String?) = prefs.edit().putString(KEY_EMAIL, email).apply()
    fun getEmail(): String? = prefs.getString(KEY_EMAIL, null)

    fun saveUserId(id: String?) = prefs.edit().putString(KEY_USER_ID, id).apply()
    fun getUserId(): String? = prefs.getString(KEY_USER_ID, null)

    fun saveRole(role: String?) = prefs.edit().putString(KEY_ROLE, role).apply()
    fun getRole(): String? = prefs.getString(KEY_ROLE, null)

    fun saveAvatar(url: String?) = prefs.edit().putString(KEY_AVATAR, url).apply()
    fun getAvatar(): String? = prefs.getString(KEY_AVATAR, null)

    fun saveUserInfo(name: String?, email: String?, avatarUrl: String?) {
        prefs.edit()
            .putString(KEY_NAME, name)
            .putString(KEY_EMAIL, email)
            .putString(KEY_AVATAR, avatarUrl)
            .apply()
    }

    fun logout() = prefs.edit().clear().apply()
}
