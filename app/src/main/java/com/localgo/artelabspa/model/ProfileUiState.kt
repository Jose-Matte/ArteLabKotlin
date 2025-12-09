package com.localgo.artelabspa.model

data class ProfileUiState(
    val userEmail: String = "",
    val userName: String = "",
    val avatarUrl: String? = null,

    val nombreEditable: String = "",
    val telefonoEditable: String = "",
    val direccionEditable: String = "",

    val isLoading: Boolean = true,
    val isUploading: Boolean = false,
    val error: String? = null
)
