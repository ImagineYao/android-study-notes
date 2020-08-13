package com.example.demo.data

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import kotlin.concurrent.thread

enum class AppType {
    USER_APP,
    SYSTEM_APP
}

data class AppInfo(
    val packageName: String,
    val appIcon: Drawable,
    val appName: String,
    val versionName: String,
    val appType: AppType
)

interface AppCallbackListener {
    fun onSuccess(apps: ArrayList<AppInfo>)
    fun onFail(e: Exception)
}

object AppUtil {
    var apps = ArrayList<AppInfo>()

    fun getAppsAsync(context: Context, listener: AppCallbackListener) {
        thread {
            try {
                val mApps = getApps(context)
                apps = mApps
                listener.onSuccess(mApps)
            } catch (e: Exception) {
                listener.onFail(e)
            }
        }
    }

    private fun getApps(context: Context): ArrayList<AppInfo> {
        val apps = ArrayList<AppInfo>()
        context.packageManager.apply {
            getInstalledPackages(PackageManager.GET_ACTIVITIES).forEach {
                val appType =
                    if (it.applicationInfo.flags.and(ApplicationInfo.FLAG_SYSTEM) == 0)
                        AppType.USER_APP
                    else AppType.SYSTEM_APP

                if (getLaunchIntentForPackage(it.packageName) != null) {
                    val appInfo = AppInfo(
                        packageName = it.packageName,
                        appIcon = it.applicationInfo.loadIcon(this),
                        appName = it.applicationInfo.loadLabel(this).toString(),
                        versionName = it.versionName,
                        appType = appType
                    )
                    apps.add(appInfo)
                }
            }
        }
        return apps
    }
}