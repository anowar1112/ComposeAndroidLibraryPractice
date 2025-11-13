package com.schedule.androidcomposelibrary.firebase.ui


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.ui.Modifier           // ✅ For Modifier
import androidx.compose.ui.unit.dp            // For dp spacing (e.g. height(8.dp))

@Composable
fun SignUpScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var parentEmail by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        Text("Sign Up", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
        OutlinedTextField(value = parentEmail, onValueChange = { parentEmail = it }, label = { Text("Parent Email") })

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            Log.d("Anowar","register button click")
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                val userId = it.user?.uid ?: return@addOnSuccessListener
                val data = hashMapOf(
                    "name" to name,
                    "email" to email,
                    "parentEmail" to parentEmail
                )
                db.collection("users").document(userId).set(data)
                Log.d("Anowar","register done")
                navController.navigate("login")
            }
        }) {
            Text("Save")
        }

        Spacer(Modifier.height(8.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Cancel")
        }
    }
}
