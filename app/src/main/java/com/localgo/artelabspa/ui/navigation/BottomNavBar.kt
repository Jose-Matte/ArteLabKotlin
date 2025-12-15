package com.localgo.artelabspa.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.localgo.artelabspa.viewmodel.CarritoViewModel

@Composable
fun BottomNavBar(
    tabs: List<String>,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onLogout: () -> Unit,
    carritoViewModel: CarritoViewModel? = null
) {
    val itemCount by (carritoViewModel?.itemCount?.collectAsState() ?: androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(0) })

    NavigationBar {

        tabs.forEachIndexed { index, tab ->

            val icon = when (tab) {
                "HOME" -> Icons.Default.Home
                "PRODUCTOS" -> Icons.Default.ShoppingBag
                "CARRITO" -> Icons.Default.ShoppingCart
                "PERFIL" -> Icons.Default.Person
                else -> Icons.Default.Home
            }

            val label = when (tab) {
                "HOME" -> "Inicio"
                "PRODUCTOS" -> "Productos"
                "CARRITO" -> "Carrito"
                "PERFIL" -> "Perfil"
                else -> tab
            }

            NavigationBarItem(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                icon = {
                    if (tab == "CARRITO" && itemCount > 0) {
                        BadgedBox(
                            badge = {
                                Badge { Text(itemCount.toString()) }
                            }
                        ) {
                            Icon(icon, contentDescription = label)
                        }
                    } else {
                        Icon(icon, contentDescription = label)
                    }
                },
                label = { Text(label) }
            )
        }

        // 🔴 Logout SIEMPRE visible
        NavigationBarItem(
            selected = false,
            onClick = onLogout,
            icon = { Icon(Icons.Default.ExitToApp, contentDescription = "Salir") },
            label = { Text("Salir") }
        )
    }
}
