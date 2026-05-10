package com.example.texi.viewmodel

import androidx.lifecycle.ViewModel
import com.example.texi.model.loginStudent

class LoginViewModel : ViewModel() {

    fun login(studentNumberInput: Int, passwordInput: String): Boolean {
        return loginStudent(studentNumberInput, passwordInput)
    }
}