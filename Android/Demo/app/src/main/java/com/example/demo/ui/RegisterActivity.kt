package com.example.demo.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.demo.R
import com.example.demo.utils.ActivityManager

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "注册"
        setContentView(R.layout.activity_register)

        ActivityManager.addActivity(this)

        findViewById<Button>(R.id.registerButton).setOnClickListener {
            val userName = findViewById<EditText>(R.id.editTexUserName).text.toString()
            val password = findViewById<EditText>(R.id.editTextPassword).text.toString()
            val editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit()

            editor.apply{
                putString("UserName", userName)
                putString("Password", password)
            }.apply()

            finish()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.removeActivity(this)
    }
}