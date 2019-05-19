package com.forcetower.news

import android.view.View
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter

@BindingAdapter("backgroundColorInt")
fun backgroundColorInt(view: View, @ColorInt color: Int) {
    view.setBackgroundColor(color)
}