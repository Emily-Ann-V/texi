package com.example.texi.ui

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.texi.R
import com.example.texi.model.LoggedInStudent
import com.example.texi.viewmodel.MyProfileViewModel

class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {

    private lateinit var viewModel: MyProfileViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MyProfileViewModel::class.java]

        val btnSave = view.findViewById<Button>(R.id.btn_my_profile_save)
        val btnMyUploads = view.findViewById<Button>(R.id.btn_my_uploads_page)

        val etFullName = view.findViewById<EditText>(R.id.et_my_profile_full_name)
        val etEmailAddress = view.findViewById<EditText>(R.id.et_my_profile_email)
        val etPassword = view.findViewById<EditText>(R.id.et_my_profile_password)

        etFullName.setText(LoggedInStudent.fullName)
        etEmailAddress.setText(LoggedInStudent.emailAddress)
        etPassword.setText(LoggedInStudent.password)

        btnSave.setOnClickListener {

            val fullNameInput = etFullName.text.toString().trim()
            val emailAddressInput = etEmailAddress.text.toString().trim()
            val passwordInput = etPassword.text.toString().trim()

            val fullNameLetterCount = fullNameInput.count { it.isLetter() }

            if (updateProfileValidation(fullNameInput, fullNameLetterCount, emailAddressInput, passwordInput)) {
                updateProfile(fullNameInput, emailAddressInput, passwordInput)
            }
        }

        btnMyUploads.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_main, MyUploadsFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    fun updateProfileValidation(
        fullNameInput: String,
        fullNameLetterCount: Int,
        emailAddressInput: String,
        passwordInput: String,
    ): Boolean {

        if (fullNameLetterCount < 5 ||
            fullNameInput.any { it.isDigit() } ||
            !fullNameInput.contains(" ")
        ) {
            Toast.makeText(
                context,
                "Full name (name + surname) must have 5+ letters and no numbers.",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddressInput).matches()) {
            Toast.makeText(context, "Please enter a valid email address.", Toast.LENGTH_LONG).show()
            return false
        }

        if (
            passwordInput.length < 8 ||
            !passwordInput.any { it.isDigit() } ||
            !passwordInput.any { !it.isLetterOrDigit() } ||
            !passwordInput.any { it.isUpperCase() } ||
            !passwordInput.any { it.isLowerCase() }
        ) {
            Toast.makeText(
                context,
                "Password must be 8+ characters and include uppercase, lowercase, number and special character.",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        return true
    }

    fun updateProfile(
        fullNameInput: String,
        emailAddressInput: String,
        passwordInput: String
    ) {
        val updated = viewModel.update(
            fullNameInput,
            emailAddressInput,
            passwordInput
        )

        if (updated) {
            Toast.makeText(context, "Profile updated.", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Email already in use. Please login.", Toast.LENGTH_LONG).show()
        }
    }
}