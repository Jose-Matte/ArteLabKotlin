package com.localgo.artelabspa.viewmodel

import com.localgo.artelabspa.data.local.SessionManager
import com.localgo.artelabspa.data.remote.dto.LoginData
import com.localgo.artelabspa.data.remote.dto.LoginResponse
import com.localgo.artelabspa.data.remote.dto.UserDto
import com.localgo.artelabspa.data.repository.AuthRepository
import com.localgo.artelabspa.utils.ValidationUtils
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var repository: AuthRepository
    private lateinit var sessionManager: SessionManager

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

        repository = mockk()
        sessionManager = mockk(relaxed = true)

        viewModel = LoginViewModel(repository, sessionManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    //  TEST - Email inválido

    @Test
    fun loginfails_with_invalid_email() = runTest {
        viewModel.onEmailChanged("correo-malo")
        viewModel.onPasswordChanged("123456")

        viewModel.login {}
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("El email no es válido", viewModel.errorMessage.value)
    }


    //  TEST — Password vacío

    @Test
    fun login_fails_with_empty_password() = runTest {
        viewModel.onEmailChanged("test@gmail.com")
        viewModel.onPasswordChanged("")

        viewModel.login {}
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("La contraseña no puede estar vacía", viewModel.errorMessage.value)
    }

    // TEST — Login exitoso

    @Test
    fun successful_login_saves_user_and_token () = runTest {
        val fakeUser = UserDto(
            _id = "1",
            email = "test@gmail.com",
            role = "CLIENTE",
            isActive = true,
            emailVerified = true
        )

        val fakeResponse = LoginResponse(
            success = true,
            message = "ok",
            data = LoginData(fakeUser, "TOKEN123")
        )

        coEvery { repository.login(any(), any()) } returns fakeResponse

        viewModel.onEmailChanged("test@gmail.com")
        viewModel.onPasswordChanged("123456")

        var called = false

        viewModel.login { called = true }
        dispatcher.scheduler.advanceUntilIdle()

        assertTrue(called)

        coVerify { sessionManager.saveToken("TOKEN123") }
        coVerify { sessionManager.saveUserId("1") }
        coVerify { sessionManager.saveEmail("test@gmail.com") }
        coVerify { sessionManager.saveRole("CLIENTE") }
    }

    //  TEST — Backend responde success = false

    @Test
    fun login_fails_when_backend_returns_error_message() = runTest {

        val fakeResponse = LoginResponse(
            success = false,
            message = "Credenciales inválidas",
            data = LoginData(
                UserDto("0", "", "", false, false),
                ""
            )
        )

        coEvery { repository.login(any(), any()) } returns fakeResponse

        viewModel.onEmailChanged("test@gmail.com")
        viewModel.onPasswordChanged("123456")

        viewModel.login {}
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("Credenciales inválidas", viewModel.errorMessage.value)
    }

    // TEST — HttpException 401

    @Test
    fun login_http_401_returns_incorrect_credentials() = runTest {

        val errorResponse = Response.error<Any>(
            401,
            ResponseBody.create(null, "")
        )

        coEvery { repository.login(any(), any()) } throws HttpException(errorResponse)

        viewModel.onEmailChanged("test@gmail.com")
        viewModel.onPasswordChanged("123456")

        viewModel.login {}
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("Correo o contraseña incorrectos", viewModel.errorMessage.value)
    }

    //  TEST — IOException (sin conexión)

    @Test
    fun login_IOException_returns_connection_error() = runTest {

        coEvery { repository.login(any(), any()) } throws IOException()

        viewModel.onEmailChanged("test@gmail.com")
        viewModel.onPasswordChanged("123456")

        viewModel.login {}
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("Problema de conexión", viewModel.errorMessage.value)
    }


    //  TEST — Error inesperado

    @Test
    fun login_unexpected_error() = runTest {

        coEvery { repository.login(any(), any()) } throws RuntimeException("Falla rara")

        viewModel.onEmailChanged("test@gmail.com")
        viewModel.onPasswordChanged("123456")

        viewModel.login {}
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("Error inesperado: Falla rara", viewModel.errorMessage.value)
    }
}
