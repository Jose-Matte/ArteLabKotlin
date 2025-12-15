package com.localgo.artelabspa.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.localgo.artelabspa.data.local.SessionManager
import com.localgo.artelabspa.data.model.UserRole
import com.localgo.artelabspa.ui.navigation.BottomNavBar
import com.localgo.artelabspa.viewmodel.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContainer(
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val carritoViewModel: CarritoViewModel = viewModel()

    val role = remember {
        UserRole.from(sessionManager.getRole())
    }

    var selectedTab by remember { mutableStateOf(0) }

    val tabs = remember(role) {
        when (role) {
            UserRole.INVITADO -> listOf("HOME", "PRODUCTOS", "CARRITO")
            UserRole.CLIENTE -> listOf("HOME", "PRODUCTOS", "CARRITO", "PERFIL")
            UserRole.VENDEDOR -> listOf("HOME", "PRODUCTOS", "CARRITO", "PERFIL")
            UserRole.ADMIN -> listOf("HOME", "PRODUCTOS", "CARRITO", "PERFIL")
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                tabs = tabs,
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                onLogout = onLogout,
                carritoViewModel = carritoViewModel
            )
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            when (tabs[selectedTab]) {

                "HOME" -> HomeScreen(
                    onNavigateToProducts = {
                        selectedTab = tabs.indexOf("PRODUCTOS")
                    },
                    onNavigateToProfile = {
                        if (tabs.contains("PERFIL")) {
                            selectedTab = tabs.indexOf("PERFIL")
                        }
                    }
                )

                "PRODUCTOS" -> ProductsScreen(
                    carritoViewModel = carritoViewModel
                )

                "CARRITO" -> CarritoScreen(
                    viewModel = carritoViewModel
                )

                "PERFIL" -> ProfileScreen(
                    onLogoutClick = onLogout
                )
            }
        }
    }
}
