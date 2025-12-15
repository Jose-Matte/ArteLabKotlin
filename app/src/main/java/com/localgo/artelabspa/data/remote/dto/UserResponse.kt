package com.localgo.artelabspa.data.remote.dto

data class UserResponse(
    val success: Boolean,
    val message: String?,
    val user: UserDto?
)
