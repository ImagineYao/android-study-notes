package com.example.demo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.demo.utils.ActivityManager

class ModifyPasswordActivity : AppCompatActivity() {
    private lateinit var originPasswordTextView: TextView
    private lateinit var newPasswordTextView: TextView
    private lateinit var confirmPasswordTextView: TextView
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "忘记密码"
        setContentView(R.layout.activity_modify_password)
        originPasswordTextView = findViewById(R.id.originPassword)
        newPasswordTextView = findViewById(R.id.newPassword)
        confirmPasswordTextView = findViewById(R.id.confirmPassword)
        sp = getSharedPreferences("data", Context.MODE_PRIVATE)
        findViewById<Button>(R.id.confirmModify).setOnClickListener { modifyPassword() }
        ActivityManager.addActivity(this)
    }

    private fun modifyPassword() {
        val originPassword = originPasswordTextView.text.toString()
        val newPassword = newPasswordTextView.text.toString()
        val confirmPassword = confirmPasswordTextView.text.toString()
        val password = sp.getString("Password", "")

        if (originPassword == "" || newPassword == "" || confirmPassword == "") {
            Toast.makeText(this, "请完成输入", Toast.LENGTH_SHORT).show()
        } else if (originPassword != password) {
            Toast.makeText(this, "原密码错误", Toast.LENGTH_SHORT).show()
        } else if (newPassword != confirmPassword) {
            Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show()
        } else {
            sp.edit {
                putString("Password", newPassword)
                if (commit()) {
                    Toast.makeText(this@ModifyPasswordActivity, "修改成功", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.removeActivity(this)
    }
}