package com.example.texi

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegister = findViewById<Button>(R.id.btn_register_submit)

        btnRegister.setOnClickListener {
            finish()
        }

        val backLink = findViewById<TextView>(R.id.tv_login_back_link)

        backLink.paintFlags = backLink.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        backLink.setOnClickListener {
            finish()
        }
    }
}