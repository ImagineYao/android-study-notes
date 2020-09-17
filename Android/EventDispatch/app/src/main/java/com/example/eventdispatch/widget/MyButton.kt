package com.example.eventdispatch.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import com.example.eventdispatch.util.EventUtil
import com.example.eventdispatch.util.LogUtil

class MyButton : AppCompatButton {
    companion object {
        const val TAG = "MyButton"
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        val eventName = EventUtil.getActionName(event)
        LogUtil.i(TAG, "dispatchTouchEvent $eventName Start", LogUtil.Depth.VIEW)
        val result = super.dispatchTouchEvent(event)
        LogUtil.i(TAG, "dispatchTouchEvent $eventName End with $result", LogUtil.Depth.VIEW)
        return result
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val eventName = EventUtil.getActionName(event)
        val result = super.onTouchEvent(event)
        LogUtil.i(TAG, "onTouchEvent $eventName return $result", LogUtil.Depth.VIEW)
        return result
    }
}