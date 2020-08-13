package com.example.demo.utils

import android.app.Activity

object ActivityManager {
    private val activities = ArrayList<Activity>()

    fun addActivity(activity: Activity) {
        if (!activities.contains(activity)) {
            activities.add(activity)
        }
    }

    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    fun finishAll() {
        for (activity in activities) {
            if (!activity.isFinishing) activity.finish()
        }
    }
}