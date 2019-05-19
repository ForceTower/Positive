package com.forcetower.news

import android.graphics.Color

data class News(
    val headline: String,
    val source: String,
    val hashtag: String = "#notíciasdodia",
    val barColor: Int = Color.parseColor("#8BC34A")
)