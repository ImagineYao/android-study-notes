package com.example.eventdispatch.ui.differentsliding

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class MyRecyclerView : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var mLastX = 0
    private var mLastY = 0

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val x = ev.x.toInt()
        val y = ev.y.toInt()
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - mLastX
                val deltaY = y - mLastY
                if (abs(deltaX) > abs(deltaY)) {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
        }
        mLastX = x
        mLastY = y
        return super.dispatchTouchEvent(ev)
    }

}