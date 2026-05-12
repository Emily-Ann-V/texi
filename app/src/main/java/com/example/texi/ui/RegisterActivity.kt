package com.example.texi.ui

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.texi.R
import com.example.texi.viewmodel.RegisterViewModel
import java.time.LocalDate

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        val etFullName = findViewById<EditText>(R.id.et_register_full_name)
        val etEmail = findViewById<EditText>(R.id.et_register_email)
        val etStudentNumber = findViewById<EditText>(R.id.et_register_student_number)
        val etUniversity = findViewById<EditText>(R.id.et_register_university)
        val etGraduationYear = findViewById<EditText>(R.id.et_register_graduation_year)
        val etField = findViewById<EditText>(R.id.et_register_field)
        val etDegree = findViewById<EditText>(R.id.et_register_degree)
        val etPassword = findViewById<EditText>(R.id.et_register_password)

        val btnRegister = findViewById<Button>(R.id.btn_register_submit)
        val btnBack = findViewById<Button>(R.id.btn_back_login)

        btnBack.setOnClickListener { finish() }

        btnRegister.setOnClickListener {

            val fullNameInput = etFullName.text.toString()
            val emailAddressInput = etEmail.text.toString()
            val universityInput = etUniversity.text.toString()
            val fieldInput = etField.text.toString()
            val degreeInput = etDegree.text.toString()
            val passwordInput = etPassword.text.toString()

            val studentNumberInput = etStudentNumber.text.toString().toIntOrNull()
            val graduationYearInput = etGraduationYear.text.toString().toIntOrNull()

            val trimmedFullNameInput = fullNameInput.trim()
            val trimmedFieldInput = fieldInput.trim()
            val trimmedDegreeInput = degreeInput.trim()

            val fullNameLetterCount = trimmedFullNameInput.count { it.isLetter() }
            val universityLetterCount = universityInput.trim().count { it.isLetter() }

            val year = LocalDate.now().year

            if (registerValidation(
                    trimmedFullNameInput,
                    fullNameLetterCount,
                    emailAddressInput,
                    studentNumberInput,
                    universityLetterCount,
                    trimmedFieldInput,
                    trimmedDegreeInput,
                    graduationYearInput,
                    passwordInput,
                    year
                )
            ) {
                registerStudent(
                    fullNameInput,
                    emailAddressInput,
                    studentNumberInput!!,
                    universityInput,
                    fieldInput,
                    degreeInput,
                    graduationYearInput!!,
                    passwordInput
                )
            }
        }
    }

    fun registerValidation(
        trimmedFullNameInput: String,
        fullNameLetterCount: Int,
        emailAddressInput: String,
        studentNumberInput: Int?,
        universityLetterCount: Int,
        trimmedFieldInput: String,
        trimmedDegreeInput: String,
        graduationYearInput: Int?,
        passwordInput: String,
        year: Int
    ): Boolean {

        if (studentNumberInput == null) {
            Toast.makeText(this, "Please enter student number.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (graduationYearInput == null) {
            Toast.makeText(this, "Please enter graduation year.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (fullNameLetterCount < 5 ||
            trimmedFullNameInput.any { it.isDigit() } ||
            !trimmedFullNameInput.contains(" ")
        ) {
            Toast.makeText(
                this,
                "Full name (name + surname) must have 5+ letters and no numbers.",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddressInput).matches()) {
            Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_LONG).show()
            return false
        }

        if (studentNumberInput.toString().length < 2) {
            Toast.makeText(this, "Student number must have 2+ numbers", Toast.LENGTH_LONG).show()
            return false
        }

        if (universityLetterCount < 5) {
            Toast.makeText(this, "University must have 5+ letters.", Toast.LENGTH_LONG).show()
            return false
        }

        if (trimmedFieldInput.length < 5 || trimmedFieldInput.any { it.isDigit() }) {
            Toast.makeText(this, "Field of study must have 5+ letters and no numbers.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (trimmedDegreeInput.length < 5 || trimmedDegreeInput.any { it.isDigit() }) {
            Toast.makeText(this, "Degree of study must have 5+ letters and no numbers.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (graduationYearInput < (year - 1) || graduationYearInput > (year + 4)) {
            Toast.makeText(
                this,
                "Graduation year must be within the last year or the next 4 years.",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        if (
            passwordInput.length < 12 ||
            !passwordInput.any { it.isDigit() } ||
            !passwordInput.any { !it.isLetterOrDigit() } ||
            !passwordInput.any { it.isUpperCase() } ||
            !passwordInput.any { it.isLowerCase() }
        ) {
            Toast.makeText(
                this,
                "Password must have 12+ uppercase, lowercase, number and special chars.",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        return true
    }

    fun registerStudent(
        fullNameInput: String,
        emailAddressInput: String,
        studentNumberInput: Int,
        universityInput: String,
        fieldInput: String,
        degreeInput: String,
        graduationYearInput: Int,
        passwordInput: String
    ) {
        val registered = viewModel.register(
            fullNameInput,
            emailAddressInput,
            studentNumberInput,
            universityInput,
            fieldInput,
            degreeInput,
            graduationYearInput,
            passwordInput
        )

        if (registered) {
            Toast.makeText(this, "Account created.", Toast.LENGTH_LONG).show()
            finish()
        } else {
            Toast.makeText(this, "User already found. Please login.", Toast.LENGTH_LONG).show()
        }
    }
}