package com.example.demo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.utils.ActivityManager
import kotlin.properties.Delegates

class LoginActivity : AppCompatActivity() {
    companion object {
        const val BACK_FROM_MAIN = "Back From MainActivity"
    }

    private lateinit var userName: String
    private lateinit var password: String
    private var isUsernameRemembered by Delegates.notNull<Boolean>()
    private var isBackFromMainActivity = false
    private var isLogged = false
    private var isLoginButtonClicked = false

    private lateinit var editTextUserName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var rememberUserName: CheckBox

    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "登录"
        setContentView(R.layout.activity_login)

        if (intent.extras != null) {
            isBackFromMainActivity = intent.extras!!.getBoolean(BACK_FROM_MAIN)!!
        }
        initView()
        ActivityManager.addActivity(this)
    }

    private fun initView() {
        sharedPrefs = getSharedPreferences("data", Context.MODE_PRIVATE)

        userName = sharedPrefs.getString("UserName", "").toString()
        password = sharedPrefs.getString("Password", "").toString()
        isUsernameRemembered = sharedPrefs.getBoolean("Remember", false)
        isLogged = sharedPrefs.getBoolean("Logged", false)

        editTextUserName = findViewById(R.id.editTexUserName)
        editTextPassword = findViewById(R.id.editTextPassword)
        rememberUserName = findViewById(R.id.rememberUserName)

        if (!isBackFromMainActivity && isUsernameRemembered && isLogged) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        if (isUsernameRemembered && userName != "" && password != "") {
            rememberUserName.isChecked = true
            editTextUserName.setText(userName)
        }

        findViewById<Button>(R.id.registerButton).setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.forgetPassword).setOnClickListener {
            val intent = Intent(this, ModifyPasswordActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.loginButton).setOnClickListener { login() }
    }

    private fun login() {
        val inputUserName = editTextUserName.text.toString()
        val inputPassword = editTextPassword.text.toString()

        if (inputUserName == "" || inputPassword == "") {
            Toast.makeText(this, "请完成输入", Toast.LENGTH_SHORT).show()
        } else if (userName == inputUserName && password == inputPassword) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            isLoginButtonClicked = true
            isLogged = true
            finish()
        } else {
            Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBackFromMainActivity) {
            isLogged = isLoginButtonClicked
        }
        sharedPrefs.edit().apply {
            putBoolean("Remember", rememberUserName.isChecked)
            putBoolean("Logged", isLogged)
        }.apply()
        ActivityManager.removeActivity(this)
    }
}