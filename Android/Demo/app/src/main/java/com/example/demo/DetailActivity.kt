package com.example.demo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.adapter.ADAPTER_POSITION
import com.example.demo.adapter.FRAGMENT_POSITION
import com.example.demo.data.AppInfo
import com.example.demo.ui.home.AppsFragment
import com.example.demo.utils.ActivityManager

class DetailActivity : AppCompatActivity() {
    private lateinit var app: AppInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val fragmentPos = intent.extras?.getInt(FRAGMENT_POSITION)
        val adapterPos = intent.extras?.getInt(ADAPTER_POSITION)

        app = when (fragmentPos) {
            0 -> AppsFragment.userApps[adapterPos!!]
            else -> AppsFragment.systemApps[adapterPos!!]
        }
        title = app.appName
        initView()
        ActivityManager.addActivity(this)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        findViewById<ImageView>(R.id.appIcon).setImageDrawable(app.appIcon)
        findViewById<TextView>(R.id.appName).text = app.appName
        findViewById<TextView>(R.id.versionName).text = app.versionName
        findViewById<Button>(R.id.operation).apply {
            setOnClickListener { getDetailAtMarket() }
        }
        val networkState = findViewById<TextView>(R.id.networkUnavailable)
        findViewById<WebView>(R.id.appDetail).apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            val key = app.appName
            if (isNetworkConnected()) {
                visibility = View.VISIBLE
                loadUrl("https://www.baike.com/wiki/$key")
            } else {
                visibility = View.GONE
                networkState.visibility = View.VISIBLE
            }
        }
    }

    private fun getDetailAtMarket() {
        val intent = Intent()
        intent.data = Uri.parse("market://detail?id=${app.packageName}")
        startActivity(intent)
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo == null) return false
        else if (networkInfo.state == NetworkInfo.State.CONNECTED) return true

        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.removeActivity(this)
    }
}