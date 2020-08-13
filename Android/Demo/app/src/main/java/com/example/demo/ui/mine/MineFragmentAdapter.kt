package com.example.demo.ui.mine

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.*
import com.example.demo.data.AppUtil
import com.example.demo.data.Setting
import com.example.demo.utils.ActivityManager

class MineFragmentAdapter(private val items: ArrayList<Setting>) :
    RecyclerView.Adapter<MineFragmentAdapter.ViewHolder>() {
    private lateinit var context: MainActivity
    private lateinit var intent: Intent

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemIcon: ImageView = view.findViewById(R.id.itemIcon)
        val itemName: TextView = view.findViewById(R.id.itemName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context as MainActivity
        val view = LayoutInflater.from(context).inflate(R.layout.mine_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            itemIcon.setImageDrawable(items[position].icon)
            itemName.text = items[position].name
            itemView.setOnClickListener { operateAt(this, position) }
        }
    }

    private fun operateAt(holder: ViewHolder, position: Int) {
        when (position) {
            0 -> {  // 修改密码
                intent = Intent(context, ModifyPasswordActivity::class.java)
                context.startActivity(intent)
            }
            1 -> {  // 关于
                intent = Intent(context, AboutActivity::class.java)
                context.startActivity(intent)
            }
            2 -> {  // 版本更新
                val versionName = getVersion()
                Toast.makeText(context, null, Toast.LENGTH_LONG).apply {
                    setText("当前版本号 $versionName\n已是最新版本")
                    show()
                }
            }
            3 -> {  // 夜间模式
                // TODO 加入切换动画，切换夜间模式
                Toast.makeText(context, "切换成功", Toast.LENGTH_SHORT).show()
                holder.itemIcon.setImageResource(R.drawable.ic_night)
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                context.application.onCreate()
            }
            4 -> {  // 退出登录
                AlertDialog.Builder(context).apply {
                    setMessage("确定退出？")
                    setPositiveButton("确定") { _, _ -> logout() }
                    setNegativeButton("取消") { _, _ -> }
                    show()
                }
            }
        }
    }

    private fun getVersion(): String {
        val thisApp = AppUtil.apps.filter { it.packageName == context.packageName }[0]
        return thisApp.versionName
    }

    private fun logout() {
        intent = Intent(context, LoginActivity::class.java)
        intent.putExtra(LoginActivity.BACK_FROM_MAIN, true)
        context.startActivity(intent)
        ActivityManager.finishAll()
    }
}