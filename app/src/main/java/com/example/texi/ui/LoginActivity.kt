package com.example.texi.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.texi.R
import com.example.texi.databinding.ActivityLoginBinding
import com.example.texi.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    // Declaring ViewModel
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting ViewModel
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.emailAddress = ""
        binding.password = ""
        binding.executePendingBindings()

        // Logging student in using function
        binding.btnLoginSubmit.setOnClickListener {

            val emailAddressInput = binding.emailAddress?.trim() ?: ""
            val passwordInput = binding.password?.trim() ?: ""

            // Validating login details
            if (loginValidation(emailAddressInput, passwordInput)) {
                loginAuthentication(emailAddressInput, passwordInput)
            }
        }

        // Opening register screen
        binding.btnRegisterPage.setOnClickListener {
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