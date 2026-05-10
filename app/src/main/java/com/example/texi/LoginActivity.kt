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
        val etStudentNumber = findViewById<EditText>(R.id.et_login_student_number)
        val etPassword = findViewById<EditText>(R.id.et_login_password)

        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {

            val stringStudentNumberInput = etStudentNumber.text.toString()
            val passwordInput = etPassword.text.toString()

            if (stringStudentNumberInput.isEmpty()) {
                Toast.makeText(this, "Please enter student number.", Toast.LENGTH_SHORT).show()
            } else if (passwordInput.isEmpty()) {
                Toast.makeText(this, "Please enter password.", Toast.LENGTH_SHORT).show()
            } else {

                val studentNumberInput = stringStudentNumberInput.toIntOrNull()

                if (studentNumberInput == null) {
                    Toast.makeText(this, "Please enter a valid student number.", Toast.LENGTH_SHORT).show()
                } else {

                    val authentication = loginStudent(
                        studentNumberInput,
                        passwordInput
                    )

                    if (authentication) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "User not found.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}