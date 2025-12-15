package com.localgo.artelabspa.utils

import android.util.Patterns.EMAIL_ADDRESS

object ValidationUtils {

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    const val ROLE_ADMIN = "ADMIN"
    const val ROLE_VENDEDOR = "VENDEDOR"
    const val ROLE_CLIENTE = "CLIENTE"
    const val ROLE_INVITADO = "INVITADO"

    fun isValidRole(role: String): Boolean {
        return role == ROLE_ADMIN ||
                role == ROLE_VENDEDOR ||
                role == ROLE_CLIENTE ||
                role == ROLE_INVITADO

    }
}

