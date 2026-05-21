package com.example.texi.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.texi.R
import com.example.texi.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    // Declaring ViewModel
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Binding UI elements
        val btnRegisterPage = findViewById<Button>(R.id.btn_register_page)
        val btnLogin = findViewById<Button>(R.id.btn_login_submit)
        val etEmailAddress = findViewById<EditText>(R.id.et_login_email_address)
        val etPassword = findViewById<EditText>(R.id.et_login_password)

        // Setting ViewModel
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        // Logging student in using function
        btnLogin.setOnClickListener {

            val emailAddressInput = etEmailAddress.text.toString().trim()
            val passwordInput = etPassword.text.toString().trim()

            // Validating login details
            if (loginValidation(emailAddressInput, passwordInput)) {
                loginAuthentication(emailAddressInput, passwordInput)
            }
        }

        // Opening register screen
        btnRegisterPage.setOnClickListener {
            loadRegisterPage()
        }
    }

    // Validating login input fields
    fun loginValidation(emailAddressInput: String, passwordInput: String): Boolean {

        if (emailAddressInput.isEmpty()) {

            Toast.makeText(
                this, "Please enter email address.", Toast.LENGTH_SHORT
            ).show()

            return false

        } else if (passwordInput.isEmpty()) {

            Toast.makeText(
                this, "Please enter password.", Toast.LENGTH_SHORT
            ).show()

            return false
        }

        return true
    }

    // Passing login details to ViewModel for authentication
    fun loginAuthentication(emailAddressInput: String, passwordInput: String) {

        val authentication = viewModel.login(
            emailAddressInput, passwordInput
        )

        // Opening home screen if login is successful
        if (authentication) {

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        } else {

            Toast.makeText(
                this, "User not found.", Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Opening register screen
    fun loadRegisterPage() {

        startActivity(
            Intent(this, RegisterActivity::class.java)
        )
    }
}