package com.localgo.artelabspa.data.remote.dto

data class RegisterRequest(
    val email: String,
    val password: String,
    val role: String,
    val nombre: String
)
