package com.example.texi.viewmodel

import androidx.lifecycle.ViewModel
import com.example.texi.model.loginStudent

class LoginViewModel : ViewModel() {

    // Two way data binding variables
    var emailAddressInput: String = ""
    var passwordInput: String = ""
    var errorMessage: String = ""

    fun login(): Boolean {

        // Logging in if validation is successful
        return if (validation()) {
            loginStudent(emailAddressInput, passwordInput)
        } else {
            false
        }
    }

    // Helper function to validate user input
    fun validation(): Boolean {

        if (emailAddressInput.isEmpty()) {
            errorMessage = "Please enter your email address."
            return false
        }
        if (passwordInput.isEmpty()) {
            errorMessage = "Please enter your password."
            return false
        }

        errorMessage = ""
        return true
    }
}