package com.example.texi.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.texi.R
import com.example.texi.databinding.FragmentUploadTextbookBinding
import com.example.texi.model.LoggedInStudent
import com.example.texi.viewmodel.AllTextbooksViewModel
import com.example.texi.viewmodel.UploadTextbookViewModel

class UploadTextbookFragment : Fragment(R.layout.fragment_upload_textbook) {

    // Declaring ViewModel
    private lateinit var viewModel: UploadTextbookViewModel
    private lateinit var binding: FragmentUploadTextbookBinding
    private lateinit var allTextbooksViewModel: AllTextbooksViewModel

    // Setting up document picker for textbook image
    private lateinit var uploadedImage: ImageView
    private var uploadedImageUri: Uri? = null
    private val uploadImage =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { imageUri: Uri? ->

            if (imageUri != null) {

                val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

                requireContext().contentResolver.takePersistableUriPermission(
                    imageUri,
                    flags
                )

                uploadedImageUri = imageUri
                uploadedImage.setImageURI(imageUri)

                viewModel.uploadedImageUriInput = imageUri
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize binding
        binding = FragmentUploadTextbookBinding.bind(view)

        // Setting ViewModels
        viewModel = ViewModelProvider(
            requireActivity()
        )[UploadTextbookViewModel::class.java
        ]
        allTextbooksViewModel =
            ViewModelProvider(requireActivity())[AllTextbooksViewModel::class.java]

        binding.uploadVM = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Binding image preview
        uploadedImage = binding.ivUploadTextbookImagePreview

        // Launching image picker (images)
        binding.ibUploadTextbookUploadImageIcon.setOnClickListener {
            uploadImage.launch(arrayOf("image/*"))
        }

        // Adding textbook to list of textbooks using function
        binding.btnUploadTextbookSubmit.setOnClickListener {

            // Getting logged in student data
            viewModel.universityInput = LoggedInStudent.university
            viewModel.fieldInput = LoggedInStudent.field
            viewModel.degreeInput = LoggedInStudent.degree
            viewModel.uploadedByInput = LoggedInStudent.emailAddress

            // Validating upload input and adding textbook to list of textbooks using functions
            if (uploadValidation()
            ) {
                uploadTextbook()
            }
        }
    }

    // Validating upload input
    fun uploadValidation(): Boolean {

        if(viewModel.uploadedImageUriInput == null
            || viewModel.titleInput.isEmpty()
            || viewModel.authorInput.isEmpty()
            || viewModel.isbnStringInput.isEmpty()
            || viewModel.descriptionInput.isEmpty()
            || viewModel.priceStringInput.isEmpty()){

            Toast.makeText(
                requireContext(),
                "Please complete all fields.",
                Toast.LENGTH_SHORT).show()
            binding.tvUploadTextbookError.visibility = View.GONE
            return false
            }

        if(!viewModel.validation()){
            binding.tvUploadTextbookError.text = viewModel.errorMessage
            binding.tvUploadTextbookError.visibility = View.VISIBLE
            return false
        }

        binding.tvUploadTextbookError.visibility = View.GONE
        return true
    }

    // Passing data to ViewModel to add to list of textbooks
    fun uploadTextbook() {

        val uploaded = viewModel.upload()

        if (uploaded) {
            Toast.makeText(requireContext(), "Textbook uploaded.", Toast.LENGTH_SHORT).show()
            allTextbooksViewModel.refresh()
            parentFragmentManager.popBackStack()
        } else {
            Toast.makeText(requireContext(), "Failed to upload textbook.", Toast.LENGTH_SHORT)
                .show()
        }
    }
}