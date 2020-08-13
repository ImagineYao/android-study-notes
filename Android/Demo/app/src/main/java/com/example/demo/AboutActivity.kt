package com.example.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demo.utils.ActivityManager

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "关于"
        setContentView(R.layout.activity_about)
        ActivityManager.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.removeActivity(this)
    }
}