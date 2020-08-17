package com.example.demo.ui.mine

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.bean.Setting

class MineFragment : Fragment() {
    private lateinit var mAdapter: MineFragmentAdapter
    private val settings = ArrayList<Setting>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sp = activity?.getSharedPreferences("data", Context.MODE_PRIVATE)!!

        view.findViewById<TextView>(R.id.userName).text = sp.getString("UserName", "")

        settings.apply {
            add(Setting(resources.getDrawable(R.drawable.ic_modify, null), "修改密码"))
            add(Setting(resources.getDrawable(R.drawable.ic_about, null), "关于"))
            add(Setting(resources.getDrawable(R.drawable.ic_update, null), "版本更新"))
            add(Setting(resources.getDrawable(R.drawable.ic_day, null), "夜间模式"))
            add(Setting(resources.getDrawable(R.drawable.ic_logout, null), "退出登录"))
        }

        mAdapter = MineFragmentAdapter(settings)
        view.findViewById<RecyclerView>(R.id.settingsRecyclerView).apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        view.findViewById<ImageView>(R.id.avatar).setOnClickListener {
            Log.d("app", "click the avatar")
        }

    }
}