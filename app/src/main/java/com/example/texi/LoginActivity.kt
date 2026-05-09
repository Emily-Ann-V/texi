package com.example.texi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnRegister = findViewById<Button>(R.id.btn_register_page)
        val btnLogin = findViewById<Button>(R.id.btn_login_submit)
        val etStudentNumberInput = findViewById<EditText>(R.id.et_login_student_number)
        val etPasswordInput = findViewById<EditText>(R.id.et_login_password)

        btnRegister.setOnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {

            val studentNumberInput = etStudentNumberInput.text.toString().toInt()
            val passwordInput = etPasswordInput.text.toString()
            val authentication = loginStudent(
                studentNumberInput,
                passwordInput
            )

            if (authentication) {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Could not find user.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}