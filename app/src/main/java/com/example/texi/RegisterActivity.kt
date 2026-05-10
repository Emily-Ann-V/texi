package com.example.texi

import android.graphics.Paint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate

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
        val backLink = findViewById<TextView>(R.id.tv_login_back_link)

        backLink.paintFlags =
            backLink.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        backLink.setOnClickListener {
            finish()
        }

        btnRegister.setOnClickListener {

            val fullNameInput = etFullName.text.toString()
            val emailAddressInput = etEmail.text.toString()
            val universityInput = etUniversity.text.toString()
            val passwordInput = etPassword.text.toString()

            val stringStudentNumberInput = etStudentNumber.text.toString()
            val stringGraduationYearInput = etGraduationYear.text.toString()

            val universityLetterCount = universityInput.trim().count { it.isLetter() }
            val year = LocalDate.now().year

            if (stringStudentNumberInput.isEmpty()) {
                Toast.makeText(this, "Please enter student number.", Toast.LENGTH_SHORT).show()
            } else if (stringGraduationYearInput.isEmpty()) {
                Toast.makeText(this, "Please enter graduation year.", Toast.LENGTH_SHORT).show()
            } else {

                val studentNumberInput = stringStudentNumberInput.toIntOrNull()
                val graduationYearInput = stringGraduationYearInput.toIntOrNull()

                if (studentNumberInput == null) {
                    Toast.makeText(this, "Please enter a valid student number.", Toast.LENGTH_SHORT).show()
                } else if (graduationYearInput == null) {
                    Toast.makeText(this, "Please enter a valid graduation year.", Toast.LENGTH_SHORT).show()
                } else {

                    if (fullNameInput.trim().length < 5 ||
                        fullNameInput.any { it.isDigit() } ||
                        !fullNameInput.contains(" ")
                    ) {
                        Toast.makeText(this, "Full name must be at least 5 letters and a space.", Toast.LENGTH_SHORT).show()
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddressInput).matches()) {
                        Toast.makeText(this, "Enter a valid email address.", Toast.LENGTH_SHORT).show()
                    } else if (stringStudentNumberInput.length < 4) {
                        Toast.makeText(this, "Student number must be at least 4 numbers", Toast.LENGTH_SHORT).show()
                    } else if (universityLetterCount < 5) {
                        Toast.makeText(this, "University have at least 5 letters.", Toast.LENGTH_SHORT).show()
                    } else if (graduationYearInput < (year - 1) || graduationYearInput > (year + 4)) {
                        Toast.makeText(this, "Graduation year must be within last year or the next 4 years.", Toast.LENGTH_SHORT).show()
                    } else if (
                        passwordInput.length < 12 ||
                        !passwordInput.any { it.isDigit() } ||
                        !passwordInput.any { !it.isLetterOrDigit() } ||
                        !passwordInput.any { it.isUpperCase() } ||
                        !passwordInput.any { it.isLowerCase() }
                    ) {
                        Toast.makeText(this, "Password must be at least 12 characters long with a uppercase and lowercase letter, number and special character.", Toast.LENGTH_SHORT).show()
                    } else {

                        val registered = registerStudent(
                            fullNameInput,
                            emailAddressInput,
                            studentNumberInput,
                            universityInput,
                            graduationYearInput,
                            passwordInput
                        )

                        if (registered) {
                            Toast.makeText(this, "Account saved.", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "User already found. Please login", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }
}