package com.example.eventdispatch.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.example.eventdispatch.ui.dispatch.EventData
import com.example.eventdispatch.util.EventUtil
import com.example.eventdispatch.util.LogUtil

class MyLayout : FrameLayout {
    companion object {
        const val TAG = "MyLayout"
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val eventName = EventUtil.getActionName(ev)
        LogUtil.i(TAG, "dispatchTouchEvent $eventName Start", LogUtil.Depth.VIEW_GROUP)
        val result = super.dispatchTouchEvent(ev)
        LogUtil.i(TAG, "dispatchTouchEvent $eventName End with $result", LogUtil.Depth.VIEW_GROUP)
        return result
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val eventName = EventUtil.getActionName(ev)
        val result = EventData.myLayoutOnInterceptEvent
        LogUtil.i(TAG, "onInterceptTouchEvent $eventName  $result", LogUtil.Depth.VIEW_GROUP)
        return result
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val eventName = EventUtil.getActionName(event)
        val result = EventData.myLayoutOnTouchEvent
        LogUtil.i(TAG, "onTouchEvent $eventName return $result", LogUtil.Depth.VIEW_GROUP)
        return result
    }
}