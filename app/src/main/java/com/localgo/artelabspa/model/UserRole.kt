package com.localgo.artelabspa.data.model

enum class UserRole {
    ADMIN,
    VENDEDOR,
    CLIENTE,
    INVITADO;

    companion object {
        fun from(value: String?): UserRole {
            return try {
                value?.let { UserRole.valueOf(it) } ?: INVITADO
            } catch (e: Exception) {
                INVITADO
            }
        }
    }
}
