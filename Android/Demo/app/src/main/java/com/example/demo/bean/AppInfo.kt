package com.example.demo.bean
import android.graphics.drawable.Drawable

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
