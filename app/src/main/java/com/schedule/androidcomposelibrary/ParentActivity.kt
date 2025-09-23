package com.schedule.androidcomposelibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ParentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // এখানে FrameLayout লাগবে container হিসেবে

        val fragmentToLoad = intent.getStringExtra("fragment") // কোন fragment load হবে

        val fragment = when (fragmentToLoad) {
            "WatchfaceMainFragment" -> WatchfaceMainFragment()
            "WatchfaceCustomFragment" -> WatchfaceCustomFragment()
            "ManageMainActivity" -> ManageMainActivity()
            else -> {
                ManageMainActivity()
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}