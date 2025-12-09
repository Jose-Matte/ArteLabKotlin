package com.localgo.artelabspa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.localgo.artelabspa.ui.navigation.AppNavigation
import com.localgo.artelabspa.ui.theme.ArteLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ArteLabTheme {
                AppNavigation()
            }
        }
    }
}
