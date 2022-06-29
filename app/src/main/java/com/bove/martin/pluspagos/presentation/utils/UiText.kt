package com.bove.martin.pluspagos.presentation.utils

import android.content.Context
import androidx.annotation.StringRes


/**
 * Created by Martín Bove on 28/6/2022.
 * E-mail: mbove77@gmail.com
 */
sealed class UiText {
    data class DynamicString(val value: String): UiText()

    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ): UiText()


    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}