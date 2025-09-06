package com.schedule.androidcomposelibrary

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class CommonUI {

    @Composable
    fun getTextObject(string: String) {
        Text(text = string)
    }
}