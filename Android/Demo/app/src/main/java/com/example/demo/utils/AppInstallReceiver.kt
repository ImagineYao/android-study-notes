package com.example.demo.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.demo.data.AppCallbackListener
import com.example.demo.data.AppInfo
import com.example.demo.data.AppUtil

class AppInstallReceiver: BroadcastReceiver(){

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == Intent.ACTION_PACKAGE_ADDED || action == Intent.ACTION_PACKAGE_REMOVED) {
            AppUtil.getAppsAsync(context, object : AppCallbackListener {
                override fun onSuccess(apps: ArrayList<AppInfo>) {
                    AppUtil.apps = apps
                }
                override fun onFail(e: Exception) {
                    e.printStackTrace()
                }
            })
        }
    }

}