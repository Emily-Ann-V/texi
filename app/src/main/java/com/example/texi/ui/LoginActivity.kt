package com.example.texi.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.texi.databinding.ActivityLoginBinding
import com.example.texi.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    // Setting ViewModel and Binding components
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialising binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting ViewModel
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]


        // Setting up data binding connections
        binding.loginVM = viewModel
        binding.lifecycleOwner = this

        // Logging student in using function
        binding.btnLoginSubmit.setOnClickListener {

            // Validating login details
            if (loginValidation()) {
                loginAuthentication()
            }
        }

        // Opening register screen
        binding.btnRegisterPage.setOnClickListener {
            loadRegisterPage()
        }
    }

    // Validating login input fields
    fun loginValidation(): Boolean {

        if (viewModel.emailAddressInput.isEmpty() && viewModel.passwordInput.isEmpty()) {

            Toast.makeText(
                this,
                "Please complete all fields.", Toast.LENGTH_SHORT
            ).show()
            binding.tvLoginError.visibility = View.GONE
            return false
        }

        if (!viewModel.validation()) {
            binding.tvLoginError.text = viewModel.errorMessage
            binding.tvLoginError.visibility = View.VISIBLE
            return false
        }

        binding.tvLoginError.visibility = View.GONE
        return true
    }

    // Passing login details to ViewModel for authentication
    fun loginAuthentication() {

        val authentication = viewModel.login()

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