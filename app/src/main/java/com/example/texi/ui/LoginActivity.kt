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

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val btnRegister = findViewById<Button>(R.id.btn_register_page)
        val btnLogin = findViewById<Button>(R.id.btn_login_submit)
        val etEmailAddress = findViewById<EditText>(R.id.et_login_email_address)
        val etPassword = findViewById<EditText>(R.id.et_login_password)

        btnRegister.setOnClickListener {
            loadRegisterPage()
        }

        btnLogin.setOnClickListener {

            val emailAddressInput = etEmailAddress.text.toString()
            val passwordInput = etPassword.text.toString()

            if (loginValidation(emailAddressInput, passwordInput)) {
                loginAuthentication(
                    emailAddressInput,
                    passwordInput
                )
            }
        }
    }

    fun loadRegisterPage() {
        startActivity(
            Intent(this, RegisterActivity::class.java)
        )
    }

    fun loginValidation(
        emailAddressInput: String,
        passwordInput: String
    ): Boolean {

        if (emailAddressInput.isEmpty()) {

            Toast.makeText(
                this,
                "Please enter email address.",
                Toast.LENGTH_SHORT
            ).show()

            return false

        } else if (passwordInput.isEmpty()) {

            Toast.makeText(
                this,
                "Please enter password.",
                Toast.LENGTH_SHORT
            ).show()

            return false
        }

        return true
    }

    fun loginAuthentication(
        emailAddressInput: String,
        passwordInput: String
    ) {

        val authentication = viewModel.login(
            emailAddressInput,
            passwordInput
        )

        if (authentication) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {

            Toast.makeText(
                this,
                "User not found.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}