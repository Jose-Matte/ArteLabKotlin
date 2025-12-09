package com.localgo.artelabspa.data.remote.dto

data class UpdateProfileRequest(
    val nombre: String? = null,
    val telefono: String? = null,
    val direccion: String? = null,
    val preferencias: List<String>? = null,
    val avatarUrl: String? = null
)
