package com.schedule.androidcomposelibrary.firebase.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.Modifier           // ✅ For Modifier
import androidx.compose.ui.unit.dp            // For dp spacing (e.g. height(8.dp))

@Composable
fun LoginScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(Modifier.padding(16.dp)) {
        Text("Login", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") })

        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    navController.navigate("chat")
                }
                .addOnFailureListener { error = it.message }
        }) {
            Text("Login")
        }

        error?.let {
            Spacer(Modifier.height(8.dp))
            Text("Error: $it", color = MaterialTheme.colorScheme.error)
        }
    }
}
