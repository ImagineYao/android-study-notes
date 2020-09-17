package com.example.eventdispatch.util

import android.view.MotionEvent

object EventUtil {
    fun getActionName(event: MotionEvent?): String {
        return when(event?.action) {
            MotionEvent.ACTION_DOWN -> "ACTION_DOWN"
            MotionEvent.ACTION_UP -> "ACTION_UP"
            MotionEvent.ACTION_MOVE -> "ACTION_MOVE"
            null -> "NULL"
            else -> "ACTION_ELSE"
        }
    }
}