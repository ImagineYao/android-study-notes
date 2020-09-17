package com.example.eventdispatch.ui.differentsliding

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventdispatch.R

class RecyclerViewAdapter(private val viewPagerType: ViewPagerType) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.list_item_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = 30

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemName = when(viewPagerType) {
            ViewPagerType.CONFLICT -> "RecyclerView Item"
            ViewPagerType.OUTER -> "RecyclerView Item"
            ViewPagerType.INNER -> "MyRecyclerView Item"
        }
        holder.textView.text = "$itemName $position"
    }
}