package com.localgo.artelabspa.model

data class ProfileUiState(
    val userName: String = "",
    val userEmail: String = "",
    val userRole: String = "",   // ✅ AGREGAR ESTO
    val avatarUrl: String? = null,
    val isLoading: Boolean = false,
    val isUploading: Boolean = false,
    val error: String? = null,

    val nombreEditable: String = "",
    val telefonoEditable: String = "",
    val direccionEditable: String = ""
)

