package com.bove.martin.pluspagos.presentation.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.bove.martin.pluspagos.R

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
           (view as TextView).text = view.context.getString(R.string.time_instant)
        } else {
           if (value >= 60) {
               (view as TextView).text = view.context.getString(R.string.time_hours, (value/60).toString())
           } else {
               (view as TextView).text = view.context.getString(R.string.time_minutes, value.toString())
           }
       }
   }
}