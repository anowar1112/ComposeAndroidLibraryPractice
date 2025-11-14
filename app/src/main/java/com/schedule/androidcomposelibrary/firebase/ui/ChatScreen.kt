package com.schedule.androidcomposelibrary.firebase.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

data class Message(
    val senderName: String,
    val senderEmail: String,
    val text: String,
    val time: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    val currentUserEmail = auth.currentUser?.email ?: ""
    val messages = remember { mutableStateListOf<Message>() }
    var input by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // Listen messages realtime
    LaunchedEffect(Unit) {
        db.collection("messages")
            .addSnapshotListener { snap, _ ->
                messages.clear()

                snap?.documents?.forEach { doc ->
                    val d = doc.data ?: return@forEach

                    messages.add(
                        Message(
                            senderName = d["senderName"].toString(),
                            senderEmail = d["senderEmail"].toString(),
                            text = d["text"].toString(),
                            time = d["time"].toString()
                        )
                    )
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
    ) {

        TopAppBar(
            title = { Text("Messages") }
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
        ) {
            items(messages) { msg ->
                ChatBubble(
                    message = msg,
                    isMe = msg.senderEmail == currentUserEmail
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Write a message...") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    scope.launch {
                        if (input.isNotEmpty()) {
                            sendMessageToParentChain(db, currentUserEmail, input)
                            input = ""
                        }
                    }
                }
            ) {
                Text("Send")
            }
        }

    }
}


@Composable
fun ChatBubble(message: Message, isMe: Boolean) {

    val bgColor = if (isMe) Color(0xFFDCF8C6) else Color.White
    val align = if (isMe) Alignment.End else Alignment.Start
    val shape =
        if (isMe) RoundedCornerShape(16.dp, 0.dp, 16.dp, 16.dp)
        else RoundedCornerShape(0.dp, 16.dp, 16.dp, 16.dp)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = align
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .clip(shape)
                .background(bgColor)
                .padding(12.dp)
                .widthIn(max = 260.dp)
        ) {

            Text(
                text = message.senderName,
                color = Color.Gray,
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = message.text,
                color = Color.Black
            )

            Spacer(Modifier.height(3.dp))

            Text(
                text = message.time,
                color = Color.DarkGray,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}


/* ---------------------------------------
  🔥 Send Message to Parent Chain
----------------------------------------*/
suspend fun sendMessageToParentChain(
    db: FirebaseFirestore,
    senderEmail: String,
    text: String
) {
    val time = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
    val visited = mutableSetOf<String>()
    var currentEmail = senderEmail

    val senderName = getUserName(db, senderEmail)

    while (true) {

        if (visited.contains(currentEmail)) break
        visited.add(currentEmail)

        val msg = hashMapOf(
            "senderName" to senderName,
            "senderEmail" to senderEmail,
            "text" to text,
            "time" to time
        )

        db.collection("messages").add(msg)

        val parent = getParentEmail(db, currentEmail)
        if (parent.isNullOrEmpty()) break

        currentEmail = parent
    }
}

suspend fun getUserName(db: FirebaseFirestore, email: String): String {
    val q = db.collection("users")
        .whereEqualTo("email", email)
        .get()
        .await()

    return q.documents.firstOrNull()?.getString("name") ?: email
}

suspend fun getParentEmail(db: FirebaseFirestore, email: String): String? {
    val q = db.collection("users")
        .whereEqualTo("email", email)
        .get()
        .await()

    return q.documents.firstOrNull()?.getString("parentEmail")
}

suspend fun sendMessageDownward(
    db: FirebaseFirestore,
    senderEmail: String,
    text: String
) {
    val time = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
    val senderName = getUserName(db, senderEmail)
    val visited = mutableSetOf<String>()

    // Recursive function
    suspend fun sendToChildren(currentEmail: String) {
        if (visited.contains(currentEmail)) return
        visited.add(currentEmail)

        // Save message for current user
        db.collection("messages").add(
            hashMapOf(
                "senderName" to senderName,
                "senderEmail" to senderEmail,
                "receiverEmail" to currentEmail,
                "text" to text,
                "time" to time
            )
        )

        // Find direct children
        val childrenQuery = db.collection("users")
            .whereEqualTo("parentEmail", currentEmail)
            .get()
            .await()

        val childrenEmails = childrenQuery.documents.mapNotNull { it.getString("email") }

        // Recursive call for each child
        for (childEmail in childrenEmails) {
            sendToChildren(childEmail)
        }
    }

    // Start recursion from sender
    sendToChildren(senderEmail)
}

