package com.cr.o.cdc.aprenderdegrandes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cr.o.cdc.aprenderdegrandes.composeScreens.ChooseVolumeCompose

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "ChooseVolume") {
                composable("ChooseVolume") { ChooseVolumeCompose(context = LocalContext.current) }
            }
        }
    }
}
