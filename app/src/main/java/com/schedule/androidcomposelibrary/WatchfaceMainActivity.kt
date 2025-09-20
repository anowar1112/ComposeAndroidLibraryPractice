package com.schedule.androidcomposelibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

class WatchfaceMainActivity  : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // এখানে আপনার Compose UI কল হবে
            Surface(color = MaterialTheme.colorScheme.background) {
                WatchFaceManagerScreen()
            }
        }
    }
}