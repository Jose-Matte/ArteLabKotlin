package com.localgo.artelabspa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.localgo.artelabspa.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import com.localgo.artelabspa.data.local.SessionManager
import com.localgo.artelabspa.utils.ValidationUtils
import android.util.Patterns.EMAIL_ADDRESS

/**/
class LoginViewModel(
    private val repository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage
    /**/


    fun onEmailChanged(newValue: String) {
        _email.value = newValue
    }

    fun onPasswordChanged(newValue: String) {
        _password.value = newValue
    }
    /**/

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {


            /**/


            //  VALIDACIONES ANTES DEL LOGIN

            if (!ValidationUtils.isValidEmail(email.value)) {
                _errorMessage.value = "El email no es válido"
                return@launch
            }

            if (password.value.isBlank()) {
                _errorMessage.value = "La contraseña no puede estar vacía"
                return@launch
            }

            //  LLAMADA AL BACKEND

            try {
                val res = repository.login(
                    email = email.value,
                    password = password.value
                )

                if (res.success) {
                    val user = res.data.user
                    val token = res.data.access_token
                    /**/


                    // Guardamos token en local
                    sessionManager.saveToken(token)

                    // Guardamos datos del usuario
                    sessionManager.saveUserId(user._id)
                    sessionManager.saveEmail(user.email)
                    sessionManager.saveRole(user.role)

                    onSuccess()

                } else {
                    _errorMessage.value = res.message
                }
                /**/


            } catch (e: HttpException) {
                _errorMessage.value = when (e.code()) {
                    401, 400 -> "Correo o contraseña incorrectos"
                    else -> "Error del servidor (${e.code()})"
                }

            } catch (e: IOException) {
                _errorMessage.value = "Problema de conexión"

            } catch (e: Exception) {
                _errorMessage.value = "Error inesperado: ${e.message}"
            }
        }
    }
}
