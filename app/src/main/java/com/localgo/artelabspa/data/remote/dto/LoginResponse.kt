package com.localgo.artelabspa.data.remote.dto

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: LoginData
)

data class LoginData(
    val user: UserDto,
    val access_token: String
)

