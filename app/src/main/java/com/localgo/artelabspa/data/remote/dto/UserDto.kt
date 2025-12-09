package com.localgo.artelabspa.data.remote.dto

data class UserDto(
    val _id: String,
    val email: String,
    val role: String,   // ← FALTABA ESTO
    val isActive: Boolean? = null,
    val emailVerified: Boolean? = null,
)
