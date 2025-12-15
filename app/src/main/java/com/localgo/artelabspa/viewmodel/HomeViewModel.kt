package com.localgo.artelabspa.viewmodel

import androidx.lifecycle.ViewModel
import com.localgo.artelabspa.data.local.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    private val sessionManager: SessionManager   // AHORA EL TIPO CORRECTO
) : ViewModel() {

    private val _userEmail = MutableStateFlow(sessionManager.getEmail() ?: "Sin correo")
    val userEmail: StateFlow<String> = _userEmail

    private val _userRole = MutableStateFlow(sessionManager.getRole() ?: "Sin rol")
    val userRole: StateFlow<String> = _userRole

    fun logout() {
        sessionManager.logout()
    }
}
