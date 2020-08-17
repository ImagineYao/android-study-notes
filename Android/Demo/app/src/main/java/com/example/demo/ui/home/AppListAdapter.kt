package com.example.demo.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.ui.DetailActivity
import com.example.demo.MainActivity
import com.example.demo.R
import com.example.demo.bean.AppInfo

const val FRAGMENT_POSITION: String = "Fragment Position"
const val ADAPTER_POSITION: String = "Item Position"

class AppListAdapter(private val apps: List<AppInfo>) :
    RecyclerView.Adapter<AppListAdapter.ViewHolder>() {

    private lateinit var context: MainActivity

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val appIcon: ImageView = view.findViewById(R.id.appIcon)
        val appName: TextView = view.findViewById(R.id.appName)
        val versionName: TextView = view.findViewById(R.id.versionName)
        val operationButton: Button = view.findViewById(R.id.operation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context as MainActivity
        val view = LayoutInflater.from(context).inflate(R.layout.home_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = apps.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            appIcon.setImageDrawable(apps[position].appIcon)
            appName.text = apps[position].appName
            versionName.text = apps[position].versionName
            operationButton.setOnClickListener { openAppAt(position) }
            itemView.setOnClickListener { showDetailAt(position) }
        }
    }

    private fun showDetailAt(position: Int) {
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra(FRAGMENT_POSITION, MainActivity.currentFragmentPosition)
            putExtra(ADAPTER_POSITION, position)
        }
        context.startActivity(intent)
    }

    private fun openAppAt(position: Int) {
        val intent = context.packageManager.getLaunchIntentForPackage(apps[position].packageName)!!
        context.startActivity(intent)
    }
}