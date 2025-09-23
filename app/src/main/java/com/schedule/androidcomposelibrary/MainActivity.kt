// MainActivity.kt
package com.schedule.androidcomposelibrary

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CommonUI().getTextObject("Hello 👋")
            Spacer(modifier = Modifier.height(10.dp)) // Text এবং Button এর মধ্যে space
            Button(onClick = {
                context.startActivity(Intent(context, SecondActivity::class.java))
            }) {
                CommonUI().getTextObject( "Go to Second Activity")
            }
            Spacer(modifier = Modifier.height(10.dp)) // Text এবং Button এর মধ্যে space
            Button(onClick = {
                val intent = Intent(context, ParentActivity::class.java)
                intent.putExtra("fragment", "WatchfaceMainFragment") // A বা B
                context.startActivity(intent)
            }) {
                CommonUI().getTextObject( "Go to watchfcae Activity")
            }
            Spacer(modifier = Modifier.height(10.dp)) // Text এবং Button এর মধ্যে space
            Button(onClick = {
                val intent = Intent(context, ParentActivity::class.java)
                intent.putExtra("fragment", "WatchfaceCustomFragment") // A বা B
                context.startActivity(intent)
            }) {
                CommonUI().getTextObject( "Go to watchfcae customize")
            }
            Spacer(modifier = Modifier.height(10.dp)) // Text এবং Button এর মধ্যে space
            Button(onClick = {
                val intent = Intent(context, ParentActivity::class.java)
                intent.putExtra("fragment", "ManageMainActivity") // A বা B
                context.startActivity(intent)
            }) {
                CommonUI().getTextObject( "Go to watchfcae manage")
            }
            GridExample()
        }
    }
}

@Composable
fun GridExample() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // ✅ এখন 'columns' ব্যবহার করতে হবে
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(20) { index ->
            Card(
                modifier = Modifier.padding(8.dp).fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    modifier = Modifier.height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Grid $index")
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
