package com.example.texi.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.texi.model.updateProfile

class MyProfileViewModel : ViewModel() {

    var fullNameInput: String = ""
    var emailAddressInput: String = ""
    var passwordInput: String = ""
    var errorMessage: String = ""

    // Passing user input to update student details
    fun update(): Boolean {

        return if (validation()) {
            updateProfile(
                fullNameInput,
                emailAddressInput,
                passwordInput
            )
        } else {
            false
        }
    }

    fun validation(): Boolean {

        val fullNameLetterCount = fullNameInput.count { it.isLetter() }

        if (fullNameLetterCount < 5 ||
            fullNameInput.any { it.isDigit() } ||
            !fullNameInput.contains(" ")
        ) {
            errorMessage = "Full name (name + surname) must have 5+ letters and no numbers."
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddressInput).matches()) {
            errorMessage = "Please enter a valid email address."
            return false
        }

        if (
            passwordInput.length < 8 ||
            !passwordInput.any { it.isDigit() } ||
            !passwordInput.any { !it.isLetterOrDigit() } ||
            !passwordInput.any { it.isUpperCase() } ||
            !passwordInput.any { it.isLowerCase() }
        ) {
            errorMessage =
                "Password must be 8+ with 1+ uppercase, lowercase, number and special char."
            return false
        }

        errorMessage = ""
        return true
    }
}