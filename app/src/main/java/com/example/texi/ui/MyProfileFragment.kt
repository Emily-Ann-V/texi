package com.example.texi.ui

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.texi.R
import com.example.texi.databinding.FragmentMyProfileBinding
import com.example.texi.model.LoggedInStudent
import com.example.texi.viewmodel.MyProfileViewModel

class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {

    // Declaring ViewModel
    private lateinit var viewModel: MyProfileViewModel
    private lateinit var binding: FragmentMyProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setting ViewModel
        viewModel = ViewModelProvider(this)[MyProfileViewModel::class.java]

        binding = FragmentMyProfileBinding.bind(view)
        binding.fullName = LoggedInStudent.fullName
        binding.emailAddress = LoggedInStudent.emailAddress
        binding.password = LoggedInStudent.password
        binding.executePendingBindings()

        // Saving updated user details using functions
        binding.btnMyProfileSave.setOnClickListener {

            val fullNameInput = binding.fullName?.trim() ?: ""
            val emailAddressInput = binding.emailAddress?.trim() ?: ""
            val passwordInput = binding.password?.trim() ?: ""

            val fullNameLetterCount = fullNameInput.count { it.isLetter() }

            // Validating profile input and updating details using functions
            if (updateProfileValidation(
                    fullNameInput,
                    fullNameLetterCount,
                    emailAddressInput,
                    passwordInput
                )
            ) {
                updateProfile(
                    fullNameInput,
                    emailAddressInput,
                    passwordInput
                )
            }
        }

        // Opening my uploads screen
        binding.btnMyUploadsPage.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_main, MyUploadsFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    // Validating profile input
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

            Toast.makeText(
                context,
                "Please enter a valid email address.",
                Toast.LENGTH_LONG
            ).show()

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
                "Password must be 8+ characters and include uppercase, " +
                        "lowercase, number and special character.",
                Toast.LENGTH_LONG
            ).show()

            return false
        }

        return true
    }

    // Updating profile details
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

            Toast.makeText(
                context,
                "Changes saved.",
                Toast.LENGTH_LONG
            ).show()

        } else {

            Toast.makeText(
                context,
                "Email already in use. Please login.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}