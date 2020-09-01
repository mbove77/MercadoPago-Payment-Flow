package com.bove.martin.pluspagos.presentation.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

/**
 * Created by Martín Bove on 31-Aug-20.
 * E-mail: mbove77@gmail.com
 */

@BindingAdapter("image")
fun loadImage(view: ImageView, url: String) {
    view.load(url) {
        crossfade(true)
    }
}