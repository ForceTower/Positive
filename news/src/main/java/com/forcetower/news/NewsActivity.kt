package com.forcetower.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, NewsStoryFragment())
                    .commit()
    }
}
