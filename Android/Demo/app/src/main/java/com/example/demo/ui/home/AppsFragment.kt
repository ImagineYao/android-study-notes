package com.example.demo.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.demo.MainActivity
import com.example.demo.R
import com.example.demo.adapter.AppListAdapter
import com.example.demo.data.AppCallbackListener
import com.example.demo.data.AppInfo
import com.example.demo.data.AppType
import com.example.demo.data.AppUtil
import kotlin.concurrent.thread

class AppsFragment(private val position: Int) : Fragment() {

    companion object {
        var userApps = ArrayList<AppInfo>()
        var systemApps = ArrayList<AppInfo>()
    }

    private lateinit var parent: MainActivity
    private lateinit var container: View
    private lateinit var myAdapter: AppListAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mLoadingText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_apps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        parent = context as MainActivity
        container = view
        setData()
    }

    private fun setData() {
        if (AppUtil.apps.size == 0) {
            AppUtil.getAppsAsync(parent, object : AppCallbackListener {
                override fun onSuccess(apps: ArrayList<AppInfo>) {
                    setApps(apps)
                }
                override fun onFail(e: Exception) {
                    e.printStackTrace()
                }
            })
        } else {
            setApps(AppUtil.apps)
        }
    }

    private fun setApps(apps: ArrayList<AppInfo>) {
        userApps = apps.filter {
            it.appType == AppType.USER_APP
        } as ArrayList<AppInfo>
        systemApps = apps.filter {
            it.appType == AppType.SYSTEM_APP
        } as ArrayList<AppInfo>

        Log.d("app", "setData")
        Log.d("app", "userApps ${userApps.size}")
        Log.d("app", "systemApps ${systemApps.size}")

        parent.runOnUiThread {
            setFragmentParams()
        }
    }

    private fun setFragmentParams() {
        myAdapter = when (position) {
            0 -> { AppListAdapter(userApps) }
            else -> { AppListAdapter(systemApps) }
        }

        container.apply {
            swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
            mLoadingText = findViewById(R.id.isLoading)

            findViewById<RecyclerView>(R.id.recyclerView).apply {
                adapter = myAdapter
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }

            findViewById<TextView>(R.id.isLoading).visibility = View.GONE
            swipeRefreshLayout.apply {
                setColorSchemeResources(R.color.colorAccent)
                visibility = View.VISIBLE
                setOnRefreshListener {
                    swipeRefreshLayout.isRefreshing = true
                    refresh(myAdapter)
                }
            }
        }

        val text = when (position) {
            0 -> StringBuilder("共找到 ${userApps.size} 款应用").toString()
            else -> StringBuilder("共找到 ${systemApps.size} 款应用").toString()
        }
        Toast.makeText(parent, text, Toast.LENGTH_SHORT).show()

        thread {
            Thread.sleep(2000)
            parent.runOnUiThread {
                container.findViewById<TextView>(R.id.appsCount).apply {
                    this.text = StringBuilder("共" + text.substring(3))
                    this.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun refresh(adapter: AppListAdapter) {
        setData()
        adapter.notifyDataSetChanged()
        swipeRefreshLayout.isRefreshing = false
        Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show()
    }
}