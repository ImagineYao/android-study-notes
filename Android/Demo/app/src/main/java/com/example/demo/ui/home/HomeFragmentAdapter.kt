package com.example.demo.ui.home

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeFragmentAdapter(homeFragment: HomeFragment) : FragmentStateAdapter(homeFragment) {

    override fun getItemCount() = HomeFragment.tabItemList.size

    override fun createFragment(position: Int): Fragment {
        Log.d("time", "HomeFragmentAdapter createFragment")
        return AppsFragment(position)
    }
}