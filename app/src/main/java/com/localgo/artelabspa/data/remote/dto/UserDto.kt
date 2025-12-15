package com.localgo.artelabspa.data.remote.dto

data class UserDto(
    val _id: String,
    val email: String,
    val role: String,
    val isActive: Boolean? = null,
    val emailVerified: Boolean? = null,
)
