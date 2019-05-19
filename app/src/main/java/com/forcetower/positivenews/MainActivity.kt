package com.forcetower.positivenews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val intent = Intent(Intent.ACTION_VIEW).setClassName(
                    "com.forcetower.positivenews",
                    "com.forcetower.news.NewsActivity"
            )
            startActivity(intent)
            finish()
        }
    }
}
