package com.schedule.androidcomposelibrary.notification.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.schedule.androidcomposelibrary.notification.viewmodel.MessageViewModel

@Composable
fun MainScreen(viewModel: MessageViewModel = viewModel()) {
    val messages by viewModel.messages.collectAsState()
    var text by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Broadcast Chat",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(messages) { msg ->
                Text("${msg.sender}: ${msg.message}")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Write a message...") }
            )
            Button(
                onClick = {
                    viewModel.sendMessage(text)
                    text = ""
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Send")
            }
        }
    }
}
