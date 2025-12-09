package com.localgo.artelabspa.model

data class LoginUiState(//estado del ui de login
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false
)
