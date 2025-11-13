package com.schedule.androidcomposelibrary.firebase.data

data class Message(
    val id: String = "",
    val fromUid: String = "",
    val fromName: String = "",
    val text: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
