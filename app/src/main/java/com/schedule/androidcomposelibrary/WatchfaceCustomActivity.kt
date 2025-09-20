package com.schedule.androidcomposelibrary

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WatchfaceCustomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // এখানে আপনার Compose UI কল হবে
            Surface(color = MaterialTheme.colorScheme.background) {
                Watchfacecustomization()
            }
        }
    }
}