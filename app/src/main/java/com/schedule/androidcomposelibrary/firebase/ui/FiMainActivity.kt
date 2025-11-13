package com.schedule.androidcomposelibrary.firebase.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class FiMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "home") {
                    composable("home") { HomeScreen(navController) }
                    composable("signup") { SignUpScreen(navController) }
                    composable("login") { LoginScreen(navController) }
                    composable("chat") { ChatScreen(navController) }
                }
            }
        }
    }
}
