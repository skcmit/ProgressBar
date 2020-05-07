package com.unacademy.test

import android.content.Context
import android.util.TypedValue

object Utils {
    fun convertDpToPixelConvert(context: Context, dp: Float): Int {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics).toInt()
    }
}