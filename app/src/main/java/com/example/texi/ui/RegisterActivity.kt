package com.example.texi.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.texi.R
import com.example.texi.viewmodel.RegisterViewModel
import java.time.LocalDate

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        val etFullName = findViewById<EditText>(R.id.et_register_full_name)
        val etEmail = findViewById<EditText>(R.id.et_register_email)
        val etStudentNumber = findViewById<EditText>(R.id.et_register_student_number)
        val spUniversity = findViewById<Spinner>(R.id.sp_register_university)
        val spField = findViewById<Spinner>(R.id.sp_register_field)
        val spDegree = findViewById<Spinner>(R.id.sp_register_degree)
        val etGraduationYear = findViewById<EditText>(R.id.et_register_graduation_year)
        val etPassword = findViewById<EditText>(R.id.et_register_password)

        val btnRegister = findViewById<Button>(R.id.btn_register_submit)
        val btnLoginPage = findViewById<Button>(R.id.btn_login_page)

        setSpinnerOptions(spUniversity, spField, spDegree)

        btnLoginPage.setOnClickListener {
            loadLoginPage()
        }

        btnRegister.setOnClickListener {

            val fullNameInput = etFullName.text.toString().trim()
            val emailAddressInput = etEmail.text.toString().trim()
            val universityInput = spUniversity.selectedItem.toString().trim()
            val fieldInput = spField.selectedItem.toString().trim()
            val degreeInput = spDegree.selectedItem.toString().trim()
            val passwordInput = etPassword.text.toString()

            val studentNumberInputString = etStudentNumber.text.toString().trim()
            val studentNumberInput = studentNumberInputString.toIntOrNull()
            val graduationYearInput = etGraduationYear.text.toString().toIntOrNull()

            val fullNameLetterCount = fullNameInput.count { it.isLetter() }

            val year = LocalDate.now().year

            if (registerValidation(
                    fullNameInput,
                    fullNameLetterCount,
                    emailAddressInput,
                    studentNumberInput,
                    studentNumberInputString,
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
        fullNameInput: String,
        fullNameLetterCount: Int,
        emailAddressInput: String,
        studentNumberInput: Int?,
        studentNumberInputString : String,
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
            fullNameInput.any { it.isDigit() } ||
            !fullNameInput.contains(" ")
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

        if (studentNumberInputString.length < 2) {
            Toast.makeText(this, "Student number must have 2+ numbers", Toast.LENGTH_LONG).show()
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
            passwordInput.length < 8 ||
            !passwordInput.any { it.isDigit() } ||
            !passwordInput.any { !it.isLetterOrDigit() } ||
            !passwordInput.any { it.isUpperCase() } ||
            !passwordInput.any { it.isLowerCase() }
        ) {
            Toast.makeText(
                this,
                "Password must have 8+ uppercase, lowercase, number and special chars.",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        return true
    }

    fun loadLoginPage() {
        startActivity(
            Intent(this, LoginActivity::class.java)
        )
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

    private fun setSpinnerOptions(
        spUniversity: Spinner,
        spField: Spinner,
        spDegree: Spinner
    ) {

        val universityAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.university_options,
            R.layout.item_register_spinner
        )

        val fieldAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.field_options,
            R.layout.item_register_spinner
        )

        val degreeAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.degree_options,
            R.layout.item_register_spinner
        )

        universityAdapter.setDropDownViewResource(R.layout.item_filter_spinner)
        fieldAdapter.setDropDownViewResource(R.layout.item_filter_spinner)
        degreeAdapter.setDropDownViewResource(R.layout.item_filter_spinner)

        spUniversity.adapter = universityAdapter
        spField.adapter = fieldAdapter
        spDegree.adapter = degreeAdapter
    }
}