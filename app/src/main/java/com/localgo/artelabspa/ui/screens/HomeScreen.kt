package com.localgo.artelabspa.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.*
import com.google.android.gms.location.LocationServices
import com.localgo.artelabspa.viewmodel.LocationViewModel
import com.localgo.artelabspa.viewmodel.WeatherViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    onNavigateToProducts: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val context = LocalContext.current

    // ViewModels
    val weatherViewModel = remember { WeatherViewModel() }
    val locationViewModel = remember { LocationViewModel() }

    val weather by weatherViewModel.weather.collectAsState()
    val locationText by locationViewModel.locationText.collectAsState()

    // Permiso ubicación
    val locationPermission = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )

    // Cargar clima
    LaunchedEffect(Unit) {
        weatherViewModel.loadWeather("Santiago")
    }

    // Obtener ubicación SOLO si hay permiso
    LaunchedEffect(locationPermission.status.isGranted) {
        if (
            locationPermission.status.isGranted &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(context)

            try {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        location?.let {
                            locationViewModel.updateLocation(it)
                        }
                    }
            } catch (e: SecurityException) {
                // seguridad extra
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Bienvenido a ArteLab",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Explora arte y bienestar",
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Clima actual 🌤️", style = MaterialTheme.typography.titleMedium)

                Spacer(Modifier.height(8.dp))

                Text(
                    text = if (weather.isNotEmpty()) weather else "Cargando clima...",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = locationText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        if (!locationPermission.status.isGranted) {
            Spacer(Modifier.height(12.dp))
            OutlinedButton(
                onClick = { locationPermission.launchPermissionRequest() }
            ) {
                Text("Permitir ubicación 📍")
            }
        }

        Spacer(Modifier.height(40.dp))

        Button(
            onClick = onNavigateToProducts,
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Ver Productos")
        }

        Spacer(Modifier.height(16.dp))

        OutlinedButton(
            onClick = onNavigateToProfile,
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Ir al Perfil")
        }
    }
}
