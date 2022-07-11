package com.bove.martin.pluspagos.presentation.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.bove.martin.pluspagos.R
import com.bove.martin.pluspagos.domain.model.Payment

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

@BindingAdapter("accreditationTime")
fun setAccreditationTime(view: View, value: Int?) {
    if (value != null) {
        if (value == 0) {
            (view as TextView).text = view.context.getString(R.string.time_instant)
        } else {
            if (value >= 60) {
                (view as TextView).text = view.context.getString(R.string.time_hours, (value / 60).toString())
            } else {
                (view as TextView).text = view.context.getString(R.string.time_minutes, value.toString())
            }
        }
    }
}

@BindingAdapter("amount")
fun setAmount(view: View, value: Double?) {
    if (value != null) {
        (view as TextView).text = "$ ${value.toInt()}"
    } else {
        (view as TextView).text = ""
    }
}

@BindingAdapter("paymentMethod")
fun setPaymentMethod(view: View, value: Payment?) {
    if (value != null) {
        (view as TextView).text = view.context.getString(R.string.pay_method, value.name)
    } else {
        (view as TextView).text = ""
    }
}
