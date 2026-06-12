package com.example.texi.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.texi.R
import com.example.texi.databinding.FragmentMyProfileBinding
import com.example.texi.model.LoggedInStudent
import com.example.texi.viewmodel.MyProfileViewModel

class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {

    // Setting ViewModel and Binding components
    private lateinit var viewModel: MyProfileViewModel
    private lateinit var binding: FragmentMyProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialising binding
        binding = FragmentMyProfileBinding.bind(view)

        // Setting ViewModel
        viewModel = ViewModelProvider(this)[MyProfileViewModel::class.java]

        // Setting up data binding connections
        binding.profileVM = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Loading logged-in student data into ViewModel
        viewModel.fullNameInput = LoggedInStudent.fullName
        viewModel.emailAddressInput = LoggedInStudent.emailAddress
        viewModel.passwordInput = LoggedInStudent.password

        // Executing pending bindings
        binding.executePendingBindings()

        // Saving updated user details using functions
        binding.btnMyProfileSave.setOnClickListener {

            // Validating profile input and updating details using functions
            if (updateProfileValidation()
            ) {
                updateProfile()
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
    fun updateProfileValidation(): Boolean {
        if (viewModel.fullNameInput.isEmpty()
            || viewModel.emailAddressInput.isEmpty()
            || viewModel.passwordInput.isEmpty()
        ) {

            Toast.makeText(
                requireContext(),
                "Please complete all fields.",
                Toast.LENGTH_SHORT
            ).show()
            binding.tvMyProfileError.visibility = View.GONE
            return false
        }

        if (!viewModel.validation()) {
            binding.tvMyProfileError.text = viewModel.errorMessage
            binding.tvMyProfileError.visibility = View.VISIBLE
            return false
        }

        binding.tvMyProfileError.visibility = View.GONE
        return true
    }

    // Updating profile details
    fun updateProfile() {

        val updated = viewModel.update()

        if (updated) {

            Toast.makeText(
                requireContext(),
                "Changes saved.",
                Toast.LENGTH_LONG
            ).show()

        } else {

            Toast.makeText(
                requireContext(),
                "Email already in use. Please login.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}