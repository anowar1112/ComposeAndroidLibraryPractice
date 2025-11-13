package com.schedule.androidcomposelibrary.firebase.data

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val parentEmail: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
