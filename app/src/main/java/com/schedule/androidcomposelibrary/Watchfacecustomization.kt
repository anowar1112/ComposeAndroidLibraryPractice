package com.schedule.androidcomposelibrary


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun Watchfacecustomization() {

    val tabs = listOf(
        "Premade", "Background", "Colour", "Hands", "Style", "Layout", "Complication 2"
    )

    var selectedTab by remember { mutableStateOf(tabs.first()) }
    var selectedFace by remember { mutableStateOf(R.drawable.unknown) }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { /* Cancel */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }
                Spacer(modifier = Modifier.width(12.dp))
                Button(
                    onClick = { /* Save */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Save")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Premium Analogue",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Big Circular Preview
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .shadow(8.dp, CircleShape)
                    .clip(CircleShape)
                    .background(Color.Black)
                    .border(2.dp, Color.Gray, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = selectedFace),
                    contentDescription = "Big Preview",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Scrollable Tabs
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tabs) { tab ->
                    FilterChip(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        label = { Text(tab) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Dynamic Content
            when (selectedTab) {
                "Complication 2" -> ComplicationTwoSection()
                else -> DefaultPreviewGrid(selectedFace) { selectedFace = it }
            }
        }
    }
}

// ======================== Complication 2 Section ========================
@Composable
fun ComplicationTwoSection() {
    val sectionList = listOf(
        "Basic" to listOf(
            "None" to R.drawable.unknown,
            "Battery" to R.drawable.unknown,
            "Compass" to R.drawable.unknown,
            "Compa" to R.drawable.unknown,
            "Compas" to R.drawable.unknown,
            "Cpass" to R.drawable.unknown,
            "Phone" to R.drawable.unknown
        ),
        "Samsung Health" to listOf(
            "Heart" to R.drawable.unknown,
            "Steps" to R.drawable.unknown,
            "Water" to R.drawable.unknown,
            "Waer" to R.drawable.unknown,
            "Sleep" to R.drawable.unknown
        ),
        "Exercise" to listOf(
            "Running" to R.drawable.unknown,
            "Cycling" to R.drawable.unknown,
            "Swimming" to R.drawable.unknown,
            "Workout" to R.drawable.unknown
        ),
        "Weather" to listOf(
            "Temperature" to R.drawable.unknown,
            "Humidity" to R.drawable.unknown,
            "UV Index" to R.drawable.unknown
        ),
        "Clock Type" to listOf(
            "Alarm" to R.drawable.unknown,
            "World Clock" to R.drawable.unknown,
            "Stopwatch" to R.drawable.unknown
        )
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(sectionList) { (sectionTitle, items) ->
            ComplicationSection(
                title = sectionTitle,
                items = items
            )
        }
    }
}

@Composable
fun ComplicationSection(title: String, items: List<Pair<String, Int>>) {
    val backgroundColors = listOf(
        Color(0xFFF7F9FC),
        Color(0xFFF9F7FC),
        Color(0xFFFCF9F7),
        Color(0xFFF8FCF7)
    )
    val bgColor = remember { backgroundColors.random() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor, shape = RoundedCornerShape(20.dp))
            .padding(12.dp)
    ) {
        // Section Header
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, bottom = 8.dp),
            color = Color.Gray
        )

        // ✅ Fixed: Add explicit height for LazyVerticalGrid
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 80.dp, max = 250.dp), // ✅ add height constraint
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(4.dp),
            userScrollEnabled = false // ✅ Prevent internal scroll conflict
        ) {
            items(items) { (label, iconRes) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { /* Handle click */ }
                        .padding(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .shadow(2.dp, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = iconRes),
                            contentDescription = label
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


// ======================== Default Grid ========================
@Composable
fun DefaultPreviewGrid(selectedFace: Int, onSelect: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        val previews = listOf(
            R.drawable.unknown,
            R.drawable.unknown,
            R.drawable.unknown,
            R.drawable.unknown,
            R.drawable.unknown,
            R.drawable.unknown
        )
        items(previews) { preview ->
            Image(
                painter = painterResource(id = preview),
                contentDescription = "WatchFace",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .border(
                        if (selectedFace == preview) 3.dp else 1.dp,
                        if (selectedFace == preview) Color.Blue else Color.Gray,
                        CircleShape
                    )
                    .padding(2.dp)
                    .clickable {
                        onSelect(preview)
                    }
            )
        }
    }
}
