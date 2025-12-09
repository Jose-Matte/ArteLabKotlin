package com.localgo.artelabspa.ui.theme

import android.app.Activity //diferentes pantallas
import android.os.Build //info del sdk
import androidx.compose.foundation.isSystemInDarkTheme//detecta si esta en modo oscuro
import androidx.compose.material3.MaterialTheme //acceso a colores, tipografía y shapes de
import androidx.compose.material3.darkColorScheme //paleta oscura
import androidx.compose.material3.dynamicDarkColorScheme //extrae colores
import androidx.compose.material3.dynamicLightColorScheme // paleta clara dinamica
import androidx.compose.material3.lightColorScheme // paleta clara
import androidx.compose.runtime.Composable //anotacion  para funciones compose
import androidx.compose.ui.platform.LocalContext //obtiene el contexto dentro del compose

//esquema de colores oscuro
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

// esquema colores claros
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

//
@Composable//funcion que dibuja ui

fun ArteLabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),//modo oscuro el sistema
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {//si hay colores dinamicos y android 12+ usa:
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)//oscuro y claro
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    //color, tipografia, contenido
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}