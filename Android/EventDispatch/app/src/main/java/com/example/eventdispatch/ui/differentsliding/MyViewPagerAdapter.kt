package com.example.eventdispatch.ui.differentsliding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyViewPagerAdapter(private val viewPagerType: ViewPagerType, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return ItemFragment(viewPagerType)
    }

    override fun getCount() = 3

    override fun getPageTitle(position: Int): CharSequence? {
        val name = when(viewPagerType) {
            ViewPagerType.CONFLICT -> "Conflict"
            ViewPagerType.OUTER -> "Outer"
            ViewPagerType.INNER -> "Inner"
        }
        return "$name $position"
    }
}