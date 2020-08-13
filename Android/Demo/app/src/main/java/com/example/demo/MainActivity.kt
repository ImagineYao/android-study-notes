package com.example.demo

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.demo.utils.ActivityManager
import com.example.demo.utils.AppInstallReceiver
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object {
        var currentFragmentPosition = 0
    }

    private lateinit var appInstallerReceiver: AppInstallReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        ActivityManager.addActivity(this)

        appInstallerReceiver = AppInstallReceiver()
        val intentFilter = IntentFilter()
        intentFilter.apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addDataScheme("package")
        }
        registerReceiver(appInstallerReceiver, intentFilter)
    }

    override fun onDestroy() {
        unregisterReceiver(appInstallerReceiver)
        super.onDestroy()
        ActivityManager.removeActivity(this)
    }
}