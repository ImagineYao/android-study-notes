package com.example.eventdispatch.ui.differentsliding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.example.eventdispatch.R
import com.google.android.material.tabs.TabLayout

/**
 * 滑动方向不一致冲突解决：
 * 1. OuterViewPager + RecyclerView （外部拦截法）
 * 2. InnerViewPager + MyRecyclerView（内部拦截法）
 */
class DifferentDirectionFragment : Fragment() {
    private lateinit var conflictViewPager: ConflictViewPager
    private lateinit var outerViewPager: OuterViewPager
    private lateinit var innerViewPager: InnerViewPager
    private lateinit var conflictTabLayout: TabLayout
    private lateinit var outerTabLayout: TabLayout
    private lateinit var innerTabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_different_direction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val conflictRadioButton: RadioButton = view.findViewById(R.id.with_conflict_radiobutton)
        val outerRadioButton: RadioButton = view.findViewById(R.id.outer_intercept_radiobutton)
        val innerRadioButton: RadioButton = view.findViewById(R.id.inner_intercept_radiobutton)

        conflictViewPager = view.findViewById(R.id.conflict_viewpager)
        conflictTabLayout = view.findViewById(R.id.conflict_tab_layout)
        outerViewPager = view.findViewById(R.id.outer_viewpager)
        outerTabLayout = view.findViewById(R.id.outer_tab_layout)
        innerViewPager = view.findViewById(R.id.inner_viewpager)
        innerTabLayout = view.findViewById(R.id.inner_tab_layout)
        // 不解决冲突
        conflictViewPager.adapter = MyViewPagerAdapter(ViewPagerType.CONFLICT, childFragmentManager)
        conflictTabLayout.setupWithViewPager(conflictViewPager)

        // 外部拦截法
        outerViewPager.adapter = MyViewPagerAdapter(ViewPagerType.OUTER, childFragmentManager)
        outerTabLayout.setupWithViewPager(outerViewPager)

        // 内部拦截法
        innerViewPager.adapter = MyViewPagerAdapter(ViewPagerType.INNER, childFragmentManager)
        innerTabLayout.setupWithViewPager(innerViewPager)
        innerTabLayout.setOnClickListener {  }

        conflictRadioButton.isChecked = true
        conflictRadioButton.setOnClickListener {
            conflictViewPager.visibility = View.VISIBLE
            outerViewPager.visibility = View.GONE
            innerViewPager.visibility = View.GONE
        }
        outerRadioButton.setOnClickListener {
            conflictViewPager.visibility = View.GONE
            outerViewPager.visibility = View.VISIBLE
            innerViewPager.visibility = View.GONE
        }
        innerRadioButton.setOnClickListener {
            conflictViewPager.visibility = View.GONE
            outerViewPager.visibility = View.GONE
            innerViewPager.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.title = resources.getString(R.string.different_sliding)
    }
}