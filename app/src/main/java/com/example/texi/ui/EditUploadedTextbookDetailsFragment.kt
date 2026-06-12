package com.example.texi.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.texi.R
import com.example.texi.databinding.FragmentEditUploadedTextbookDetailsBinding
import com.example.texi.model.LoggedInStudent
import com.example.texi.viewmodel.AllTextbooksViewModel
import com.example.texi.viewmodel.EditUploadedTextbookDetailsViewModel

class EditUploadedTextbookDetailsFragment :
    Fragment(R.layout.fragment_edit_uploaded_textbook_details) {

    // Binding UI elements
    private lateinit var viewModel: EditUploadedTextbookDetailsViewModel
    private lateinit var binding: FragmentEditUploadedTextbookDetailsBinding
    private lateinit var allTextbooksViewModel: AllTextbooksViewModel

    // Setting up document picker for textbook image
    private lateinit var updatedUploadedImage: ImageView
    private var updatedUploadedImageUri: Uri? = null
    private val updatedUploadImage =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { imageUri: Uri? ->

            if (imageUri != null) {

                val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

                requireContext().contentResolver.takePersistableUriPermission(
                    imageUri,
                    flags
                )

                updatedUploadedImageUri = imageUri
                updatedUploadedImage.setImageURI(imageUri)

                viewModel.uploadedImageUriInput = imageUri
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Receiving textbook data from previous screen
        val uploadedImageResId = arguments?.getInt("uploadedImageResId")
        val uploadedImageUri = arguments?.getString("uploadedImageUri")
        val title = arguments?.getString("title") ?: ""
        val author = arguments?.getString("author") ?: ""
        val isbn = arguments?.getLong("isbn") ?: 0L
        val description = arguments?.getString("description") ?: ""
        val price = arguments?.getFloat("price") ?: 0f

        // Initialize binding
        binding = FragmentEditUploadedTextbookDetailsBinding.bind(view)

        // Setting ViewModels
        viewModel = ViewModelProvider(requireActivity())[EditUploadedTextbookDetailsViewModel::class.java]
        allTextbooksViewModel = ViewModelProvider(requireActivity())[AllTextbooksViewModel::class.java]

        binding.editVM = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Set initial values from arguments into ViewModel
        viewModel.titleInput = title
        viewModel.authorInput = author
        viewModel.isbnStringInput = isbn.toString()
        viewModel.descriptionInput = description
        viewModel.priceStringInput = price.toString()
        viewModel.uploadedImageUriInput = uploadedImageUri?.toUri()


        // Binding image view for updates
        updatedUploadedImage = binding.ivEditUploadedTextbookDetailsCoverImage

        // Displaying textbook image (resource or URI fallback)
        if (uploadedImageResId != null && uploadedImageResId != 0) {
            binding.ivEditUploadedTextbookDetailsCoverImage.setImageResource(uploadedImageResId)
        } else if (!uploadedImageUri.isNullOrEmpty()) {
            binding.ivEditUploadedTextbookDetailsCoverImage.setImageURI(uploadedImageUri.toUri())
        }

        // Set logged in student details manually
        binding.tvEditUploadedTextbookDetailsUniversity.text = LoggedInStudent.university
        binding.tvEditUploadedTextbookDetailsField.text = LoggedInStudent.field
        binding.tvEditUploadedTextbookDetailsDegree.text = LoggedInStudent.degree

        // Execute pending bindings
        binding.executePendingBindings()

        // Returning to previous screen
        binding.ibEditUploadedTextbookBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Launching image picker (images)
        binding.ibEditUploadedTextbookDetailsEditCoverImageIcon.setOnClickListener {
            updatedUploadImage.launch(arrayOf("image/*"))
        }

        // Updating textbook details using functions
        binding.btnEditUploadedTextbookDetailsSave.setOnClickListener {

            if (updateUploadValidation()
            ) {
                updateUploadedTextbook(isbn)
            }
        }

        // Deleting textbook using function
        binding.btnUploadedTextbookDelete.setOnClickListener {
            deleteTextbook(isbn)
        }
    }

    // Validating user input before updating
    fun updateUploadValidation(): Boolean {

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
            binding.tvEditTextbookError.visibility = View.GONE
            return false
        }

        if(!viewModel.validation()){
            binding.tvEditTextbookError.text = viewModel.errorMessage
            binding.tvEditTextbookError.visibility = View.VISIBLE
            return false
        }

        binding.tvEditTextbookError.visibility = View.GONE
        return true
    }

    // Passing user input for updating textbook
    fun updateUploadedTextbook(currentISBN: Long) {

        val updated = viewModel.update(currentISBN)

        if (updated) {
            Toast.makeText(requireContext(),
                "Changes saved.",
                Toast.LENGTH_SHORT).show()
            allTextbooksViewModel.refresh()
            parentFragmentManager.popBackStack()
        } else {
            Toast.makeText(requireContext(), "Failed to save changes.", Toast.LENGTH_SHORT).show()
        }
    }

    // Passing user input for deleting textbook
    fun deleteTextbook(currentISBN: Long) {
        val deleted = viewModel.delete(currentISBN)
        if (deleted) {
            Toast.makeText(requireContext(), "Textbook deleted.", Toast.LENGTH_SHORT).show()
            allTextbooksViewModel.refresh()
            parentFragmentManager.popBackStack()
        } else {
            Toast.makeText(requireContext(), "Failed to delete textbook.", Toast.LENGTH_SHORT).show()
        }
    }
}