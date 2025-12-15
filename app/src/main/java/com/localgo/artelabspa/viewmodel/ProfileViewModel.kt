package com.localgo.artelabspa.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.localgo.artelabspa.data.local.SessionManager
import com.localgo.artelabspa.data.repository.AvatarRepositoryBackend
import com.localgo.artelabspa.model.ProfileUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    application: Application,
    private val avatarRepository: AvatarRepositoryBackend,
    private val sessionManager: SessionManager,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUserInfo()
    }

    // --------- Cargar información inicial ---------
    private fun loadUserInfo() {
        viewModelScope.launch(dispatcher) {
            val email = sessionManager.getEmail()
            val avatarUrl = sessionManager.getAvatar()
            val role = sessionManager.getRole()

            val userName = email?.substringBefore("@")
                ?.replaceFirstChar { it.uppercase() }
                ?: "Usuario"

            _uiState.update {
                it.copy(
                    userEmail = email ?: "Sin correo",
                    userName = userName,
                    userRole = role ?: "invitado",
                    avatarUrl = avatarUrl,

                    // inicializamos campos editables
                    nombreEditable = userName,
                    telefonoEditable = "",
                    direccionEditable = "",

                    isLoading = false
                )
            }
        }
    }

    // --------- Campos editables ---------
    fun onNombreChanged(newValue: String) {
        _uiState.update { it.copy(nombreEditable = newValue) }
    }

    fun onTelefonoChanged(newValue: String) {
        _uiState.update { it.copy(telefonoEditable = newValue) }
    }

    fun onDireccionChanged(newValue: String) {
        _uiState.update { it.copy(direccionEditable = newValue) }
    }

    // --------- Guardar datos en backend ---------
    fun saveProfileChanges() {
        viewModelScope.launch(dispatcher) {
            val state = uiState.value

            val ok = avatarRepository.updateProfileData(
                nombre = state.nombreEditable,
                telefono = state.telefonoEditable,
                direccion = state.direccionEditable
            )

            _uiState.update {
                it.copy(
                    userName = if (ok && state.nombreEditable.isNotBlank())
                        state.nombreEditable
                    else
                        it.userName,
                    error = if (ok) null else "No se pudieron guardar los cambios"
                )
            }
        }
    }

    // --------- Subir avatar ---------
    fun uploadAvatar(uri: Uri) {
        viewModelScope.launch(dispatcher) {
            Log.d("PROFILE", "Subiendo avatar desde URI: $uri")

            _uiState.update { it.copy(isUploading = true, error = null) }

            val newUrl = avatarRepository.uploadAvatar(uri)

            if (newUrl != null) {
                Log.d("PROFILE", "Avatar actualizado: $newUrl")

                _uiState.update {
                    it.copy(
                        avatarUrl = newUrl,
                        isUploading = false,
                        error = null
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isUploading = false,
                        error = "Error subiendo imagen"
                    )
                }
            }
        }
    }
}
