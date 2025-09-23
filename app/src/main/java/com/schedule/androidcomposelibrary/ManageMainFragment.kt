package com.schedule.androidcomposelibrary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.compose.ui.platform.ComposeView

class ManageMainActivity : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                // Material theme wrapper
                MaterialTheme {
                    MaterialTheme {
                        WatchFaceScreen(
                            onBackClick = { activity?.finish() } // Back button action
                        )
                    }
                }
            }
        }
    }
}
// ---------------- Screen ----------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchFaceScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Watch Face Manager") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFEFEFEF)) // পুরো ব্যাকগ্রাউন্ড হালকা রঙ
        ) {
            // RecyclerView (Grid) এর মতো সেকশন
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
                    .clip(RoundedCornerShape(16.dp)) // radius
                    .background(Color.White)        // আলাদা bg
            ) {
                val sample = listOf(
                    WatchFace(1, "Live wallpaper", android.R.drawable.ic_menu_gallery, isSelected = true),
                    WatchFace(2, "Ball", android.R.drawable.ic_menu_camera, isSelected = false),
                    WatchFace(3, "My style", android.R.drawable.ic_menu_report_image, isSelected = false),
                    WatchFace(4, "Circle Dashboard", android.R.drawable.ic_menu_gallery, isSelected = false),
                    WatchFace(5, "Dashboard", android.R.drawable.ic_menu_report_image, isSelected = false),
                    WatchFace(6, "Circle", android.R.drawable.ic_menu_camera, isSelected = false),
                    WatchFace(1, "Live wallpaper", android.R.drawable.ic_menu_gallery, isSelected = false),
                    WatchFace(2, "Ball", android.R.drawable.ic_menu_camera, isSelected = false),
                    WatchFace(3, "My style", android.R.drawable.ic_menu_report_image, isSelected = false),
                    WatchFace(4, "Circle Dashboard", android.R.drawable.ic_menu_gallery, isSelected = false),
                    WatchFace(5, "Dashboard", android.R.drawable.ic_menu_report_image, isSelected = false),
                    WatchFace(6, "Circle", android.R.drawable.ic_menu_camera, isSelected = false),
                    WatchFace(1, "Live wallpaper", android.R.drawable.ic_menu_gallery, isSelected = false),
                    WatchFace(2, "Ball", android.R.drawable.ic_menu_camera, isSelected = false),
                    WatchFace(3, "My style", android.R.drawable.ic_menu_report_image, isSelected = false),
                    WatchFace(4, "Circle Dashboard", android.R.drawable.ic_menu_gallery, isSelected = false),
                    WatchFace(5, "Dashboard", android.R.drawable.ic_menu_report_image, isSelected = false),
                    WatchFace(6, "Circle", android.R.drawable.ic_menu_camera, isSelected = false)
                )
                WatchFaceGrid(
                    watchFaces = sample,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
