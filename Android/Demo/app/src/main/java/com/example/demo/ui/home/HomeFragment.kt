package com.example.demo.ui.home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.demo.MainActivity
import com.example.demo.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    companion object {
        val tabItemList = listOf("第三方应用", "系统应用")
    }
    private lateinit var adapter: HomeFragmentAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createAppsFragment(view)
    }

    @SuppressLint("InflateParams")
    private fun getTabView(position: Int): View {
        val view = LayoutInflater.from(activity).inflate(R.layout.tab_item, null)
        val tabItem: TextView = view.findViewById(R.id.tab_item)
        tabItem.text = tabItemList[position]
        return view
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun updateTabView(tab: TabLayout.Tab, isSelected: Boolean) {
        val textView: TextView? = tab.customView?.findViewById(R.id.tab_item)
        if (isSelected) {
            textView?.setTextAppearance(R.style.TabItemSelected)
        } else {
            textView?.setTextAppearance(R.style.TabItemUnSelected)
        }
    }

    private fun createAppsFragment(view: View) {
        adapter = HomeFragmentAdapter(this)

        viewPager = view.findViewById(R.id.viewPager)

        viewPager.adapter = adapter
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.customView = getTabView(position)
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    MainActivity.currentFragmentPosition = tab.position
                    updateTabView(tab, true)
                }
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    updateTabView(tab, false)
                }
            }
        })

        tabLayoutMediator.attach()
    }
}