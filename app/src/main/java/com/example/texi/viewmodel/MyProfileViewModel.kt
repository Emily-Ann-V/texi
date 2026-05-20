package com.example.texi.viewmodel

import androidx.lifecycle.ViewModel
import com.example.texi.model.updateProfile

class MyProfileViewModel: ViewModel() {

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