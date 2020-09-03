package com.bove.martin.pluspagos.presentation.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
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

@BindingAdapter("acreditationTime")
fun setVisibility(view: View, value: Int?) {
    if (value != null) {
        if (value == 0) {
           (view as TextView).text = "Instantáneo"
        } else {
           if (value >= 60) {
               (view as TextView).text = "${(value / 60)} Horas"
           } else {
               (view as TextView).text = "${(value / 60)} Minutos"
           }
       }
   }
}