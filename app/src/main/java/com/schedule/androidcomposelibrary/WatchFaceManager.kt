package com.schedule.androidcomposelibrary

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class WatchFaceManager {
}
// ------------------- Data Model -------------------
data class WatchFace(
    val id: Int,
    val name: String,
    val imageRes: Int,
    val isSelected: Boolean = false // ✅ নতুন প্রোপার্টি
)

// ------------------- Grid -------------------
@Composable
fun WatchFaceGrid(
    modifier: Modifier = Modifier,
    watchFaces: List<WatchFace>
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
        modifier = modifier.padding(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(watchFaces) { item ->
            WatchFaceItem(item)
        }
    }
}

// ------------------- Single Item -------------------
@Composable
fun WatchFaceItem(item: WatchFace) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier.size(100.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .then(
                        if (item.isSelected)
                            Modifier.border(width = 3.dp, color = Color.Blue, shape = CircleShape)
                        else
                            Modifier
                    ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // ✅ Conditional Icon
            if (item.isSelected) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color.Blue,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = (8).dp, y = (-8).dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = (8).dp, y = (-8).dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.name,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// ------------------- Preview -------------------
@Preview(showBackground = true)
@Composable
fun PreviewWatchFaceGrid() {
    val sample = listOf(
        WatchFace(1, "Live wallpaper", android.R.drawable.ic_menu_gallery, isSelected = true),
        WatchFace(2, "Ball", android.R.drawable.ic_menu_camera, isSelected = false),
        WatchFace(3, "My style", android.R.drawable.ic_menu_report_image, isSelected = false),
        WatchFace(4, "Circle Dashboard", android.R.drawable.ic_menu_gallery, isSelected = false),
    )
    WatchFaceGrid(watchFaces = sample)
}