package com.example.texi.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.texi.R
import com.example.texi.databinding.FragmentEditUploadedTextbookDetailsBinding
import com.example.texi.model.LoggedInStudent
import com.example.texi.model.Textbook
import com.example.texi.viewmodel.AllTextbooksViewModel
import com.example.texi.viewmodel.EditUploadedTextbookDetailsViewModel

class EditUploadedTextbookDetailsFragment :
    Fragment(R.layout.fragment_edit_uploaded_textbook_details) {

    private lateinit var binding: FragmentEditUploadedTextbookDetailsBinding

    // Binding UI elements
    private lateinit var viewModel: EditUploadedTextbookDetailsViewModel
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
            }
        }

    // Suppressing warning
    @SuppressLint("CutPasteId", "SetTextI18n")

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

        // Create textbook object from arguments
        val textbook = Textbook(
            uploadedImageResId = uploadedImageResId,
            uploadedImageUri = uploadedImageUri?.toUri(),
            title = title,
            author = author,
            isbn = isbn,
            description = description,
            price = price,
            university = "",
            field = "",
            degree = "",
            uploadedBy = LoggedInStudent.emailAddress
        )

        // Bind textbook to layout
        binding.textbook = textbook
        binding.isbnString = isbn.toString()
        binding.priceString = price.toString()

        // Setting ViewModels
        viewModel = ViewModelProvider(requireActivity())[EditUploadedTextbookDetailsViewModel::class.java]
        allTextbooksViewModel = ViewModelProvider(requireActivity())[AllTextbooksViewModel::class.java]

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
            val uploadedImageResIdInput = null
            val uploadedImageUriInput =
                if (updatedUploadedImageUri != null) {
                    updatedUploadedImageUri
                } else {
                    uploadedImageUri?.toUri()
                }

            val titleInput = binding.textbook?.title?.trim() ?: ""
            val authorInput = binding.textbook?.author?.trim() ?: ""
            val descriptionInput = binding.textbook?.description?.trim() ?: ""
            val isbnInput = binding.isbnString?.toLongOrNull() ?: 0L
            val priceInput = binding.priceString?.toFloatOrNull() ?: 0f

            val titleLetterCount = titleInput.count { it.isLetter() }
            val authorLetterCount = authorInput.count { it.isLetter() }
            val descriptionLetterCount = descriptionInput.count { it.isLetter() }
            val isbnInputString = isbnInput.toString()

            if (updateUploadValidation(
                    uploadedImageUriInput,
                    titleLetterCount,
                    authorLetterCount,
                    isbnInputString,
                    descriptionLetterCount,
                    priceInput
                )
            ) {
                updateUploadedTextbook(
                    isbn,
                    uploadedImageResIdInput,
                    uploadedImageUriInput,
                    titleInput,
                    authorInput,
                    isbnInput,
                    descriptionInput,
                    priceInput
                )
            }
        }

        // Deleting textbook using function
        binding.btnUploadedTextbookDelete.setOnClickListener {
            deleteTextbook(isbn)
        }
    }

    // Validating user input before updating
    fun updateUploadValidation(
        uploadedImageUriInput: Uri? = null,
        titleLetterCount: Int,
        authorLetterCount: Int,
        isbnInputString: String,
        descriptionLetterCount: Int,
        priceInput: Float?,
    ): Boolean {

        if (uploadedImageUriInput == null) {
            Toast.makeText(context, "Please upload an image.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (titleLetterCount < 5) {
            Toast.makeText(context, "Title must have 5+ letters.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (authorLetterCount < 5) {
            Toast.makeText(context, "Author must have 5+ letters.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (isbnInputString.length != 13) {
            Toast.makeText(context, "ISBN must be 13 numbers.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (descriptionLetterCount < 10) {
            Toast.makeText(context, "Description must have 10+ letters.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (priceInput == null || (priceInput < 20f)) {
            Toast.makeText(context, "Price must be R20+.", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    // Passing user input for updating textbook
    fun updateUploadedTextbook(
        currentISBN: Long,
        uploadedImageResIdInput: Int? = null,
        uploadedImageUriInput: Uri? = null,
        titleInput: String,
        authorInput: String,
        isbnInput: Long,
        descriptionInput: String,
        priceInput: Float
    ) {

        val updated = viewModel.update(
            currentISBN,
            uploadedImageResIdInput,
            uploadedImageUriInput,
            titleInput,
            authorInput,
            isbnInput,
            descriptionInput,
            priceInput
        )

        if (updated) {
            Toast.makeText(context, "Changes saved.", Toast.LENGTH_SHORT).show()
            allTextbooksViewModel.refresh()
            parentFragmentManager.popBackStack()
        } else {
            Toast.makeText(context, "Failed to save changes.", Toast.LENGTH_SHORT).show()
        }
    }

    // Passing user input for deleting textbook
    fun deleteTextbook(currentISBN: Long) {
        val deleted = viewModel.delete(currentISBN)
        if (deleted) {
            Toast.makeText(context, "Textbook deleted.", Toast.LENGTH_SHORT).show()
            allTextbooksViewModel.refresh()
            parentFragmentManager.popBackStack()
        } else {
            Toast.makeText(context, "Failed to delete textbook.", Toast.LENGTH_SHORT).show()
        }
    }
}