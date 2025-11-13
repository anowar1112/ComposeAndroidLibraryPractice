package com.schedule.androidcomposelibrary.firebase.ui


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Modifier           // ✅ For Modifier
import androidx.compose.ui.unit.dp            // For dp spacing (e.g. height(8.dp))

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigate("login") }, modifier = androidx.compose.ui.Modifier.fillMaxWidth()) {
            Text("Login")
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = { navController.navigate("signup") }, modifier = androidx.compose.ui.Modifier.fillMaxWidth()) {
            Text("Sign Up")
        }
    }
}
