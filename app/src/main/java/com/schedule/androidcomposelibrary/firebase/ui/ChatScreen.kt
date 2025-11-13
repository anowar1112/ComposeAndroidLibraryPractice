package com.schedule.androidcomposelibrary.firebase.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.Modifier           // ✅ For Modifier
import androidx.compose.ui.unit.dp            // For dp spacing (e.g. height(8.dp))

data class Message(val sender: String, val text: String, val time: String)

@Composable
fun ChatScreen(navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val messages = remember { mutableStateListOf<Message>() }
    var input by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    // 🔹 Load all messages in realtime
    LaunchedEffect(Unit) {
        db.collection("messages")
            .addSnapshotListener { snapshot, _ ->
                messages.clear()
                snapshot?.documents?.forEach { doc ->
                    val data = doc.data ?: return@forEach
                    messages.add(
                        Message(
                            sender = data["sender"].toString(),
                            text = data["text"].toString(),
                            time = data["time"].toString()
                        )
                    )
                }
            }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Messages", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        LazyColumn(Modifier.weight(1f)) {
            items(messages) { msg ->
                Text("📩 ${msg.sender}: ${msg.text} (${msg.time})")
                Spacer(Modifier.height(6.dp))
            }
        }

        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Type your message") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    sendMessageToParentChain(db, user?.email ?: "", input)
                    input = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send")
        }
    }
}

/**
 * 🔹 এই ফাংশনটি recursive ভাবে parent chain খুঁজে বের করে
 * এবং প্রতিটি parent কে message পাঠায়।
 */
suspend fun sendMessageToParentChain(db: FirebaseFirestore, senderEmail: String, text: String) {
    val time = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

    // Step 1: নিজের জন্য message save করো
    val selfMessage = hashMapOf(
        "sender" to senderEmail,
        "text" to text,
        "time" to time
    )
    db.collection("messages").add(selfMessage)

    // Step 2: parent খুঁজে বের করো এবং recursive ভাবে মেসেজ পাঠাও
    var currentEmail = senderEmail
    val visited = mutableSetOf<String>()

    while (true) {
        if (visited.contains(currentEmail)) break
        visited.add(currentEmail)

        // ইউজারের parent ইমেইল বের করো
        val parentEmail = getParentEmail(db, currentEmail)
        if (parentEmail == null || parentEmail.isEmpty()) break

        // parent কে message পাঠাও
        val parentMessage = hashMapOf(
            "sender" to senderEmail,
            "text" to text,
            "time" to time,
            "to" to parentEmail
        )
        db.collection("messages").add(parentMessage)
        currentEmail = parentEmail
    }
}

/**
 * 🔹 এই ফাংশনটি Firestore থেকে parentEmail রিটার্ন করে
 */
suspend fun getParentEmail(db: FirebaseFirestore, email: String): String? {
    val query = db.collection("users")
        .whereEqualTo("email", email)
        .get()
        .await()

    val doc = query.documents.firstOrNull()
    return doc?.getString("parentEmail")
}
