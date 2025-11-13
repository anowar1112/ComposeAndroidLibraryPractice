package com.schedule.androidcomposelibrary.notification.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.schedule.androidcomposelibrary.notification.data.MessageRepository


class MessageViewModel : ViewModel() {

    private val repo = MessageRepository()
    val messages = repo.messages

    init {
        viewModelScope.launch {
            repo.observeMessages()
        }
    }

    fun sendMessage(text: String) {
        viewModelScope.launch {
            repo.sendMessage(sender = "User", text = text)
        }
    }
}
