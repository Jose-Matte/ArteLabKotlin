package com.localgo.artelabspa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localgo.artelabspa.data.repository.AuthRepository
import com.localgo.artelabspa.utils.ValidationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _successMessage = MutableStateFlow("")
    val successMessage: StateFlow<String> = _successMessage

    // VALIDACIONES AUTOMÁTICAS
    val isNameValid: Boolean
        get() = name.value.length >= 3

    val isEmailValid: Boolean
        get() = ValidationUtils.isValidEmail(email.value)

    val isPasswordValid: Boolean
        get() = password.value.length >= 6

    val isFormValid: Boolean
        get() = isNameValid && isEmailValid && isPasswordValid

    // ------------------------------

    fun onNameChanged(newValue: String) {
        _name.value = newValue
    }

    fun onEmailChanged(newValue: String) {
        _email.value = newValue.trim()
    }

    fun onPasswordChanged(newValue: String) {
        _password.value = newValue
    }

    fun register(onSuccess: () -> Unit) {
        if (!isFormValid) {
            _errorMessage.value = "Revisa los datos ingresados."
            return
        }

        viewModelScope.launch {
            try {
                val res = repository.register(
                    nombre = name.value,
                    email = email.value,
                    password = password.value
                )

                if (res.success) {
                    _successMessage.value = "Registro exitoso"
                    onSuccess()
                } else {
                    _errorMessage.value = res.message
                }

            } catch (e: HttpException) {
                when (e.code()) {
                    409 -> _errorMessage.value = "Ese correo ya está registrado"
                    400 -> _errorMessage.value = "Datos inválidos"
                    else -> _errorMessage.value = "Error del servidor: ${e.code()}"
                }
            }
        }
    }
}
