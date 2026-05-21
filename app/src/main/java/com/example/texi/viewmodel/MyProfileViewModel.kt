package com.example.texi.viewmodel

import androidx.lifecycle.ViewModel
import com.example.texi.model.updateProfile

class MyProfileViewModel : ViewModel() {

    // Passing user input to update student details
    fun update(
        fullNameInput: String,
        emailAddressInput: String,
        passwordInput: String
    ): Boolean {

        return updateProfile(
            fullNameInput,
            emailAddressInput,
            passwordInput
        )
    }
}