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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.localgo.artelabspa.data.local.SessionManager
import com.localgo.artelabspa.data.remote.RetrofitClient
import com.localgo.artelabspa.data.repository.AuthRepository
import com.localgo.artelabspa.viewmodel.RegisterViewModel
import com.localgo.artelabspa.utils.ValidationUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    val context = LocalContext.current

    val sessionManager = remember { SessionManager(context) }
    val api = remember { RetrofitClient.createApiService() }
    val authRepository = remember { AuthRepository(api, sessionManager) }
    val viewModel = remember { RegisterViewModel(authRepository, sessionManager) }

    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val role by viewModel.role.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val successMessage by viewModel.successMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8FC))
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(80.dp))

        Text(
            text = "Crear Cuenta",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6C63FF)
        )

        Text(
            text = "ArteLab SPA",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(Modifier.height(32.dp))

        // ---------- NOMBRE ----------
        OutlinedTextField(
            value = name,
            onValueChange = viewModel::onNameChanged,
            label = { Text("Nombre completo") },
            leadingIcon = { Icon(Icons.Default.Person, null) },
            singleLine = true,
            isError = !viewModel.isNameValid && name.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        )
        if (!viewModel.isNameValid && name.isNotEmpty()) {
            Text("El nombre es muy corto", color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(14.dp))

        // ---------- EMAIL ----------
        OutlinedTextField(
            value = email,
            onValueChange = viewModel::onEmailChanged,
            label = { Text("Correo electrónico") },
            leadingIcon = { Icon(Icons.Default.Email, null) },
            singleLine = true,
            isError = !viewModel.isEmailValid && email.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        )
        if (!viewModel.isEmailValid && email.isNotEmpty()) {
            Text("Correo inválido", color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(14.dp))

        // ---------- PASSWORD ----------
        OutlinedTextField(
            value = password,
            onValueChange = viewModel::onPasswordChanged,
            label = { Text("Contraseña (mínimo 6 caracteres)") },
            leadingIcon = { Icon(Icons.Default.Lock, null) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = !viewModel.isPasswordValid && password.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        )
        if (!viewModel.isPasswordValid && password.isNotEmpty()) {
            Text("La contraseña es muy corta", color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(14.dp))

        // ---------- SELECTOR DE ROL ----------
        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = when (role) {
                    ValidationUtils.ROLE_CLIENTE -> "Cliente"
                    ValidationUtils.ROLE_VENDEDOR -> "Vendedor"
                    ValidationUtils.ROLE_ADMIN -> "Administrador"
                    else -> "Selecciona un rol"
                },
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo de usuario") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                isError = !viewModel.isRoleValid && role.isNotEmpty()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Cliente") },
                    onClick = {
                        viewModel.onRoleChanged(ValidationUtils.ROLE_CLIENTE)
                        expanded = false
                    }
                )

                DropdownMenuItem(
                    text = { Text("Vendedor") },
                    onClick = {
                        viewModel.onRoleChanged(ValidationUtils.ROLE_VENDEDOR)
                        expanded = false
                    }
                )

                DropdownMenuItem(
                    text = { Text("Administrador") },
                    onClick = {
                        viewModel.onRoleChanged(ValidationUtils.ROLE_ADMIN)
                        expanded = false
                    }
                )
            }
        }

        if (!viewModel.isRoleValid && role.isNotEmpty()) {
            Text(
                "Debes seleccionar un rol válido",
                color = MaterialTheme.colorScheme.error
            )
        }


        if (!viewModel.isRoleValid && role.isNotEmpty()) {
            Text("Debes seleccionar un rol válido", color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(24.dp))

        // ---------- BOTÓN REGISTRAR ----------
        Button(
            onClick = { viewModel.register(onRegisterSuccess) },
            enabled = viewModel.isFormValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6C63FF)
            )
        ) {
            Text("Registrarse", fontWeight = FontWeight.SemiBold)
        }

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = onBackToLogin) {
            Text("Volver al inicio de sesión")
        }

        if (errorMessage.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(errorMessage, color = MaterialTheme.colorScheme.error)
        }

        if (successMessage.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(successMessage, color = Color(0xFF6C63FF))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    MaterialTheme {
        RegisterScreen(
            onRegisterSuccess = {},
            onBackToLogin = {}
        )
    }
}
