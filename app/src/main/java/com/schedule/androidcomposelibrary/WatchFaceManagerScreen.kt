package com.schedule.androidcomposelibrary

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp


@Composable
fun WatchFaceManagerScreen() {
    val density = LocalDensity.current
    var headerHeight by remember { mutableStateOf(0.dp) }

    val previewMaxHeight = 200.dp
    val previewMinHeight = previewMaxHeight / 2
    var previewHeight by remember { mutableStateOf(previewMaxHeight) }

    val categoryData = List(10) { index ->
        val itemCount = (5..20).random()
        "Category $index" to List(itemCount) { "Item $it" }
    }


    Box(modifier = Modifier.fillMaxSize()) {

        // Scrollable content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = headerHeight + previewHeight)
                .background(Color(0xFFF5F5F5)),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            categoryData.forEach { (title, items) ->
                item { CategorySection(title = title, items = items) }
            }
        }

        // Fixed Header + Resizable Preview
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .background(Color.White)
                .onGloballyPositioned { coordinates ->
                    headerHeight = with(density) { coordinates.size.height.toDp() } - previewHeight
                }
        ) {
            HeaderSection()

            // Resizable PreviewSection
            val previewMaxSize = 180.dp
            val previewMinSize = 90.dp
            val fraction = (previewHeight - previewMinHeight) / (previewMaxHeight - previewMinHeight)
            val itemSize = previewMinSize + (previewMaxSize - previewMinSize) * fraction

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(previewHeight)
                    .background(Color.LightGray)
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { change, dragAmount ->
                            change.consume()
                            previewHeight = (previewHeight + dragAmount.toDp())
                                .coerceIn(previewMinHeight, previewMaxHeight)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                PreviewSectionContent(itemSize)
            }
        }
    }
}

// Extracted preview content so it doesn't depend on height
@Composable
fun PreviewSectionContent(itemSize: Dp) { // pass itemSize as parameter
    val previewItems = listOf(
        R.drawable.unknown,
        R.drawable.unknown,
        R.drawable.unknown,
        R.drawable.unknown
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(previewItems) { item ->

            Box(
                modifier = Modifier
                    .size(itemSize)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = item),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }
        }
    }
}


// Extension to convert pixels to dp inside drag gestures
fun Float.toDp(): Dp = (this / Resources.getSystem().displayMetrics.density).dp

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Explore Galaxy watch fa...",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

//@Composable
//fun PreviewSection() {
//    val previewItems = listOf(
//        R.drawable.unknown,
//        R.drawable.unknown,
//        R.drawable.unknown,
//        R.drawable.unknown
//    )
//
//    LazyRow(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        horizontalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        items(previewItems) { item ->
//            // বড় Watch preview
//            Box(
//                modifier = Modifier
//                    .size(180.dp)
//                    .clip(CircleShape)
//                    .background(Color.LightGray),
//                contentAlignment = Alignment.Center
//            ) {
//                Image(
//                    painter = painterResource(id = item),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(100.dp)
//                        .clip(RoundedCornerShape(12.dp))
//                )
//            }
//        }
//    }
//}


@Composable
fun CategorySection(title: String, items: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(items.size) { index ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Img") // এখানে আসল watch face image বসাবেন
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(items[index], fontSize = 14.sp)
                }
            }
        }
    }
}
