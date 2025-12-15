package com.localgo.artelabspa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
//archivos locales
import com.localgo.artelabspa.data.repository.AuthRepository
import com.localgo.artelabspa.utils.ValidationUtils
import com.localgo.artelabspa.data.local.SessionManager
class RegisterViewModel(
    private val repository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _role = MutableStateFlow("")
    val role: StateFlow<String> = _role

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _successMessage = MutableStateFlow("")
    val successMessage: StateFlow<String> = _successMessage

    val isNameValid get() = name.value.length >= 3
    val isEmailValid get() = ValidationUtils.isValidEmail(email.value)
    val isPasswordValid get() = password.value.length >= 6
    val isRoleValid get() = ValidationUtils.isValidRole(role.value)

    val isFormValid get() =
        isNameValid && isEmailValid && isPasswordValid && isRoleValid

    fun onNameChanged(value: String) { _name.value = value }
    fun onEmailChanged(value: String) { _email.value = value.trim() }
    fun onPasswordChanged(value: String) { _password.value = value }
    fun onRoleChanged(value: String) { _role.value = value }

    fun register(onSuccess: () -> Unit) {
        if (!isFormValid) {
            _errorMessage.value = "Revisa los datos ingresados"
            return
        }

        viewModelScope.launch {
            try {
                val res = repository.register(
                    nombre = name.value,
                    email = email.value,
                    password = password.value,
                    role = role.value
                )

                if (res.success) {
                    _successMessage.value = "Registro exitoso"
                    onSuccess()
                } else {
                    _errorMessage.value = res.message
                }

            } catch (e: HttpException) {
                _errorMessage.value = when (e.code()) {
                    409 -> "Correo ya registrado"
                    400 -> "Datos inválidos"
                    else -> "Error del servidor"
                }
            }
        }
    }
}

