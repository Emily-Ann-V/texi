package com.example.texi.viewmodel

import androidx.lifecycle.ViewModel
import com.example.texi.model.loginStudent

class LoginViewModel : ViewModel() {

    // Passing user input to log student in
    fun login(emailAddressInput: String, passwordInput: String): Boolean {
        return loginStudent(emailAddressInput, passwordInput)
    }
}