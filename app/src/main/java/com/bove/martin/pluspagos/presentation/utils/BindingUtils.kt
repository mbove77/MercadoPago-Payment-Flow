package com.bove.martin.pluspagos.presentation.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("image")
fun loadImage(view: ImageView, url: String) {
    view.load(url) {
        crossfade(true)
    }
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.GONE else View.VISIBLE
}