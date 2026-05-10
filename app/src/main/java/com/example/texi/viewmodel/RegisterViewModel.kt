package com.example.texi.viewmodel

import androidx.lifecycle.ViewModel
import com.example.texi.model.registerStudent

class RegisterViewModel : ViewModel() {

    fun register(
        fullNameInput: String,
        emailAddressInput: String,
        studentNumberInput: Int,
        universityInput: String,
        graduationYearInput: Int,
        passwordInput: String
    ): Boolean {

        return registerStudent(
            fullNameInput,
            emailAddressInput,
            studentNumberInput,
            universityInput,
            graduationYearInput,
            passwordInput
        )
    }
}