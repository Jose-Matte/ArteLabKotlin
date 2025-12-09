package com.localgo.artelabspa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.localgo.artelabspa.data.local.SessionManager
import com.localgo.artelabspa.data.remote.RetrofitClient
import com.localgo.artelabspa.data.repository.AuthRepository
import com.localgo.artelabspa.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    val context = LocalContext.current

    val sessionManager = remember { SessionManager(context) }
    val api = remember { RetrofitClient.createApiService() }
    val authRepository = remember { AuthRepository(api, sessionManager) }
    val viewModel = remember { RegisterViewModel(authRepository) }

    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val successMessage by viewModel.successMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8FC)) // Fondo suave
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(Modifier.height(80.dp))

        // TÍTULO
        Text(
            "Crear Cuenta",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6C63FF) // Morado elegante
        )

        Text(
            "ArteLab SPA",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(Modifier.height(32.dp))

        // CAMPO NOMBRE
        OutlinedTextField(
            value = name,
            onValueChange = viewModel::onNameChanged,
            label = { Text("Nombre completo") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            singleLine = true,
            isError = !viewModel.isNameValid && name.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp)),
            shape = RoundedCornerShape(14.dp)
        )
        if (!viewModel.isNameValid && name.isNotEmpty()) {
            Text("El nombre es muy corto", color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(14.dp))

        // CAMPO EMAIL
        OutlinedTextField(
            value = email,
            onValueChange = viewModel::onEmailChanged,
            label = { Text("Correo electrónico") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            singleLine = true,
            isError = !viewModel.isEmailValid && email.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp)),
            shape = RoundedCornerShape(14.dp)
        )
        if (!viewModel.isEmailValid && email.isNotEmpty()) {
            Text("Correo inválido", color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(14.dp))

        // CAMPO CONTRASEÑA
        OutlinedTextField(
            value = password,
            onValueChange = viewModel::onPasswordChanged,
            label = { Text("Contraseña (mínimo 6 caracteres)") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = !viewModel.isPasswordValid && password.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp)),
            shape = RoundedCornerShape(14.dp)
        )
        if (!viewModel.isPasswordValid && password.isNotEmpty()) {
            Text("La contraseña es muy corta", color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(24.dp))

        // BOTÓN REGISTRARSE
        Button(
            onClick = { viewModel.register(onRegisterSuccess) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            enabled = viewModel.isFormValid,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6C63FF)
            ),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text("Registrarse", fontWeight = FontWeight.SemiBold)
        }

        Spacer(Modifier.height(16.dp))

        // BOTÓN VOLVER A LOGIN
        TextButton(onClick = onBackToLogin) {
            Text(
                "Volver al inicio de sesión",
                color = Color(0xFF3F3D56),
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(Modifier.height(8.dp))

        // ERROR MENSAJE
        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        }

        // SUCCESS MENSAJE
        if (successMessage.isNotEmpty()) {
            Text(successMessage, color = Color(0xFF6C63FF))
        }
    }
}
