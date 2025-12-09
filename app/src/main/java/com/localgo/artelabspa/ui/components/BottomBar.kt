package com.localgo.artelabspa.ui.components

import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class NavItem(
    val label: String,
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun BottomBar(
    currentRoute: String?,
    onItemClick: (String) -> Unit
) {
    val items = listOf(
        NavItem("Productos", "productos", Icons.Default.Home),
        NavItem("Perfil", "perfil", Icons.Default.Person),
        NavItem("Salir", "logout", Icons.Default.ExitToApp)
    )

    NavigationBar(containerColor = Color(0xFFF0EEFF)) {

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onItemClick(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
