package com.localgo.artelabspa.data.remote.dto

data class ClienteProfileResponse(
    val nombre: String,
    val telefono: String?,
    val direccion: String?,
    val avatarUrl: String?
)
