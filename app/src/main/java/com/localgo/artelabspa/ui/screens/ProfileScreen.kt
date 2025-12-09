package com.localgo.artelabspa.ui.screens

import android.app.Application
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.localgo.artelabspa.data.local.SessionManager
import com.localgo.artelabspa.data.remote.api.AvatarApiService
import com.localgo.artelabspa.data.repository.AvatarRepositoryBackend
import com.localgo.artelabspa.viewmodel.ProfileViewModel
import com.localgo.artelabspa.viewmodel.ProfileViewModelFactory

@Composable
fun ProfileScreen(
    onLogoutClick: () -> Unit = {}
) {

    val context = LocalContext.current

    val viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(
            application = context.applicationContext as Application,
            avatarRepository = AvatarRepositoryBackend(
                api = AvatarApiService.create(),
                sessionManager = SessionManager(context),
                context = context
            ),
            sessionManager = SessionManager(context)
        )
    )

    val uiState by viewModel.uiState.collectAsState()

    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) viewModel.uploadAvatar(uri)
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // TÍTULO
        Text(
            text = "Mi Perfil",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // ---------- AVATAR ----------
        Box(
            modifier = Modifier.size(140.dp),
            contentAlignment = Alignment.BottomEnd
        ) {

            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0))
                    .clickable { imagePickerLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                when {
                    uiState.isUploading -> {
                        CircularProgressIndicator()
                    }

                    !uiState.avatarUrl.isNullOrEmpty() -> {
                        AsyncImage(
                            model = uiState.avatarUrl,
                            contentDescription = "Avatar",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    else -> {
                        Text(
                            text = uiState.userName.firstOrNull()?.uppercase() ?: "A",
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.DarkGray
                        )
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable { imagePickerLauncher.launch("image/*") },
                color = MaterialTheme.colorScheme.primary,
                shadowElevation = 6.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Cambiar foto",
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Text(uiState.userName, style = MaterialTheme.typography.titleMedium)
        Text(uiState.userEmail, color = Color.Gray)

        Spacer(Modifier.height(24.dp))

        // ---------- CAMPOS ----------
        OutlinedTextField(
            value = uiState.nombreEditable,
            onValueChange = viewModel::onNombreChanged,
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.userEmail,
            onValueChange = {},
            enabled = false,
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.telefonoEditable,
            onValueChange = viewModel::onTelefonoChanged,
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.direccionEditable,
            onValueChange = viewModel::onDireccionChanged,
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { viewModel.saveProfileChanges() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Guardar Cambios")
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = onLogoutClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.Red
            )
        ) {
            Text("Cerrar sesión")
        }

        if (uiState.error != null) {
            Spacer(Modifier.height(12.dp))
            Text(uiState.error!!, color = Color.Red)
        }
    }
}
