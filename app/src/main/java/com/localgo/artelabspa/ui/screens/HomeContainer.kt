package com.localgo.artelabspa.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.localgo.artelabspa.ui.navigation.BottomNavBar

import com.localgo.artelabspa.ui.screens.HomeScreen
import com.localgo.artelabspa.ui.screens.ProductsScreen
import com.localgo.artelabspa.ui.screens.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContainer(
    onLogout: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        // La barra de navegación inferior
        bottomBar = {
            BottomNavBar(
                selectedTab = selectedTab,
                onTabSelected = { newTab -> selectedTab = newTab },
                onLogout = onLogout
            )
        }
    ) { innerPadding ->

        Column(Modifier.padding(innerPadding)) {

            // 'when' actúa como un "switch" para mostrar la pantalla correcta
            // según la pestaña seleccionada.
            when (selectedTab) {
                // Si la pestaña es 0, muestra HomeScreen
                0 -> HomeScreen(
                    // Le pasamos la acción para cambiar a la pestaña de Productos (1)
                    onNavigateToProducts = { selectedTab = 1 },
                    // Le pasamos la acción para cambiar a la pestaña de Perfil (2)
                    onNavigateToProfile = { selectedTab = 2 }
                )
                // Si la pestaña es 1, muestra ProductsScreen
                1 -> ProductsScreen()

                // Si la pestaña es 2, muestra ProfileScreen
                2 -> ProfileScreen(onLogoutClick = onLogout)
            }
        }
    }
}
