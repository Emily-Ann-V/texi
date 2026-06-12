package com.example.texi.viewmodel

import android.annotation.SuppressLint
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.texi.model.registerStudent
import java.time.LocalDate

class RegisterViewModel : ViewModel() {

    var fullNameInput: String = ""
    var emailAddressInput: String = ""
    var studentNumberInput: String = ""
    var universityInput: String = ""
    var fieldInput: String = ""
    var degreeInput: String = ""
    var graduationYearInput: String = ""
    var passwordInput: String = ""
    var errorMessage: String = ""

    // Passing user input to register student (add to list of students)
    fun register(): Boolean {
        return if (validation()) {
            registerStudent(
                fullNameInput,
                emailAddressInput,
                studentNumberInput.toIntOrNull() ?: 0,
                universityInput,
                fieldInput,
                degreeInput,
                graduationYearInput.toIntOrNull() ?: 0,
                passwordInput
            )
        } else {
            false
        }
    }

    @SuppressLint("NewApi")
    fun validation(): Boolean {

        val fullNameLetterCount = fullNameInput.count { it.isLetter() }
        val studentNumberInputString = studentNumberInput.trim()
        val year = LocalDate.now().year

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

        if (studentNumberInputString.length < 2) {
            errorMessage = "Student number must have 2+ numbers."
            return false
        }

        val graduationYear = graduationYearInput.toIntOrNull()
        if (graduationYear == null || graduationYear < (year - 1) || graduationYear > (year + 4)) {
            errorMessage = "Graduation year must be within the last year or the next 4 years."
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