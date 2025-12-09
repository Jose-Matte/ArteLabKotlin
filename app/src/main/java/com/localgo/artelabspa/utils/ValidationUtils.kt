package com.localgo.artelabspa.utils

import android.util.Patterns.EMAIL_ADDRESS

object ValidationUtils {
    fun isValidEmail(email: String): Boolean {
        // Usa el validador estándar de Android para correos
        return EMAIL_ADDRESS.matcher(email).matches()
    }
}
