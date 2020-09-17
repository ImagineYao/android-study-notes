package com.example.eventdispatch

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.eventdispatch.ui.dispatch.EventData
import com.example.eventdispatch.util.EventUtil
import com.example.eventdispatch.util.LogUtil

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "Activity"
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = resources.getString(R.string.event_dispatch)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val eventName = EventUtil.getActionName(ev)
        LogUtil.i(TAG, "dispatchTouchEvent $eventName Start", LogUtil.Depth.ACTIVITY)
        val result = super.dispatchTouchEvent(ev)
        LogUtil.i(TAG, "dispatchTouchEvent $eventName End with $result", LogUtil.Depth.ACTIVITY)
        return result
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val eventName = EventUtil.getActionName(event)
        val result = EventData.activityOnTouchEvent
        LogUtil.i(TAG, "onTouchEvent $eventName return $result", LogUtil.Depth.ACTIVITY)
        return result
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_event -> {
                navigateTo(R.id.nav_event)
                true
            }
            R.id.menu_same_direction -> {
                navigateTo(R.id.nav_same)
                true
            }
            R.id.menu_different_direction -> {
                navigateTo(R.id.nav_different)
                true
            }
            else -> false
        }
    }

    private fun navigateTo(resId: Int) {
        findNavController(R.id.nav_host_fragment).navigate(resId)
    }
}