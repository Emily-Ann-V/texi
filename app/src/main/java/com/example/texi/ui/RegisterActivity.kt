package com.example.texi.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.texi.R
import com.example.texi.databinding.ActivityRegisterBinding
import com.example.texi.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    // Declaring ViewModel
    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting ViewModel
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        binding.registerVM = viewModel
        binding.lifecycleOwner = this

        // Setting spinner options
        setSpinnerOptions(binding.spRegisterUniversity,
            binding.spRegisterField,
            binding.spRegisterDegree
        )

        // Adding user to list of students using functions
        binding.btnRegisterSubmit.setOnClickListener {

            // Save spinner selections to ViewModel
            viewModel.universityInput = binding.spRegisterUniversity.selectedItem.toString()
            viewModel.fieldInput = binding.spRegisterField.selectedItem.toString()
            viewModel.degreeInput = binding.spRegisterDegree.selectedItem.toString()

            // Validating registration input and adding student to list of students using functions
            if (registerValidation()) {
                registerStudent()
            }
        }

        // Opening login screen
        binding.btnLoginPage.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
        }
    }

    // Validating registration input
    fun registerValidation(): Boolean {

        if (viewModel.fullNameInput.isEmpty()
            || viewModel.emailAddressInput.isEmpty()
            || viewModel.studentNumberInput.isEmpty()
            || viewModel.graduationYearInput.isEmpty()
            || viewModel.passwordInput.isEmpty()) {

            Toast.makeText(
                this,
                "Please complete all fields.",
                Toast.LENGTH_SHORT).show()
            binding.tvRegisterError.visibility = View.GONE
            return false
        }

        if (!viewModel.validation()) {
            binding.tvRegisterError.text = viewModel.errorMessage
            binding.tvRegisterError.visibility = View.VISIBLE
            return false
        }

        binding.tvRegisterError.visibility = View.GONE
        return true
    }

    // Passing registration data to ViewModel to add to list of students
    fun registerStudent() {

        val registered = viewModel.register()

        if (registered) {

            Toast.makeText(this, "Account created.", Toast.LENGTH_LONG).show()
            finish()

        } else {

            Toast.makeText(
                this,
                "User already found. Please login.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // Setting spinner options
    private fun setSpinnerOptions(
        spUniversity: Spinner,
        spField: Spinner,
        spDegree: Spinner
    ) {

        val universityAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.university_options,
            R.layout.item_register_spinner
        )

        val fieldAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.field_options,
            R.layout.item_register_spinner
        )

        val degreeAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.degree_options,
            R.layout.item_register_spinner
        )

        universityAdapter.setDropDownViewResource(R.layout.item_filter_spinner)
        fieldAdapter.setDropDownViewResource(R.layout.item_filter_spinner)
        degreeAdapter.setDropDownViewResource(R.layout.item_filter_spinner)

        spUniversity.adapter = universityAdapter
        spField.adapter = fieldAdapter
        spDegree.adapter = degreeAdapter
    }
}