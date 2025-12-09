package com.localgo.artelabspa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// --- CORRECCIÓN 1: Añadir el parámetro de navegación ---
@Composable
fun HomeScreen(
    onNavigateToProducts: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Bienvenido a ArteLab", style = MaterialTheme.typography.headlineMedium)
        Text("Explora arte y bienestar", color = MaterialTheme.colorScheme.primary)

        Spacer(Modifier.height(40.dp))

        Button(
            // --- CORRECCIÓN 2: Llamar a la función recibida ---
            onClick = onNavigateToProducts,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Ver Productos")
        }

        Spacer(Modifier.height(16.dp))

        OutlinedButton(
            // --- CORRECCIÓN 3 (opcional, pero buena práctica): Conectar el otro botón ---
            onClick = onNavigateToProfile,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Ir al Perfil")
        }
    }
}
