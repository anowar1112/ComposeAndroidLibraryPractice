package com.schedule.androidcomposelibrary

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView


class WatchfaceMainFragment  : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                // এখানে আপনার Compose UI কল হবে
                Surface(color = MaterialTheme.colorScheme.background) {
                    WatchFaceManagerScreen()
                }
            }
        }
    }
}
