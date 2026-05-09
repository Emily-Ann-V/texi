package com.example.texi

import android.graphics.Paint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etFullName = findViewById<EditText>(R.id.et_register_full_name)
        val etEmail = findViewById<EditText>(R.id.et_register_email)
        val etStudentNumber = findViewById<EditText>(R.id.et_register_student_number)
        val etUniversity = findViewById<EditText>(R.id.et_register_university)
        val etGraduationYear = findViewById<EditText>(R.id.et_register_graduation_year)
        val etPassword = findViewById<EditText>(R.id.et_register_password)
        val btnRegister = findViewById<Button>(R.id.btn_register_submit)

        btnRegister.setOnClickListener {

            val fullNameInput = etFullName.text.toString()
            val emailAddressInput = etEmail.text.toString()
            val studentNumberInput = etStudentNumber.text.toString().toInt()
            val universityInput = etUniversity.text.toString()
            val graduationYearInput = etGraduationYear.text.toString().toInt()
            val passwordInput = etPassword.text.toString()

            val registered = registerStudent(
                fullNameInput,
                emailAddressInput,
                studentNumberInput,
                universityInput,
                graduationYearInput,
                passwordInput
            )

            if (registered) {
                Toast.makeText(this, "User saved.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "User already found. Please login", Toast.LENGTH_SHORT).show()
            }
        }

        val backLink =
            findViewById<TextView>(R.id.tv_login_back_link)

        backLink.paintFlags =
            backLink.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        backLink.setOnClickListener {
            finish()
        }
    }
}