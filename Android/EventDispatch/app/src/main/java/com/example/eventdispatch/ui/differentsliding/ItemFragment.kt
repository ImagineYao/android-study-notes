package com.example.eventdispatch.ui.differentsliding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventdispatch.R

class ItemFragment(private val viewPagerType: ViewPagerType) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        val myRecyclerView: MyRecyclerView = view.findViewById(R.id.my_recycler_view)
        recyclerView.apply {
            adapter = RecyclerViewAdapter(viewPagerType)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        myRecyclerView.apply {
            adapter = RecyclerViewAdapter(viewPagerType)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        when (viewPagerType) {
            ViewPagerType.CONFLICT -> {
                recyclerView.visibility = View.VISIBLE
                myRecyclerView.visibility = View.GONE
            }
            ViewPagerType.OUTER -> {
                recyclerView.visibility = View.VISIBLE
                myRecyclerView.visibility = View.GONE
            }
            ViewPagerType.INNER -> {
                recyclerView.visibility = View.GONE
                myRecyclerView.visibility = View.VISIBLE
            }
        }
    }
}