package com.example.texi.viewmodel

import androidx.lifecycle.ViewModel
import com.example.texi.model.loginStudent

class LoginViewModel : ViewModel() {

    var emailAddressInput: String = ""
    var passwordInput: String = ""
    var errorMessage: String = ""

    fun login(): Boolean {
        return if (validation()) {
            loginStudent(emailAddressInput, passwordInput)
        } else {
            false
        }
    }

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