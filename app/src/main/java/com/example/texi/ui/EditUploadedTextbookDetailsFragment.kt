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
import com.example.texi.model.LoggedInStudent
import com.example.texi.viewmodel.EditUploadedTextbookDetailsViewModel

class EditUploadedTextbookDetailsFragment :
    Fragment(R.layout.fragment_edit_uploaded_textbook_details) {

    // Binding UI elements
    private lateinit var viewModel: EditUploadedTextbookDetailsViewModel

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
        val title = arguments?.getString("title")
        val author = arguments?.getString("author")
        val isbn = arguments?.getLong("isbn")
        val description = arguments?.getString("description")
        val price = arguments?.getFloat("price")

        // Binding UI elements
        val ibBack = view.findViewById<ImageButton>(R.id.ib_edit_uploaded_textbook_back)
        val ibEdit =
            view.findViewById<ImageButton>(R.id.ib_edit_uploaded_textbook_details_edit_cover_image_icon)
        val btnSave = view.findViewById<Button>(R.id.btn_edit_uploaded_textbook_details_save)
        val btnDelete = view.findViewById<Button>(R.id.btn_uploaded_textbook_delete)
        val tvUniversity = view.findViewById<TextView>(
            R.id.tv_edit_uploaded_textbook_details_university)
        val tvField = view.findViewById<TextView>(R.id.tv_edit_uploaded_textbook_details_field)
        val tvDegree = view.findViewById<TextView>(R.id.tv_edit_uploaded_textbook_details_degree)
        val ivImage =
            view.findViewById<ImageView>(R.id.iv_edit_uploaded_textbook_details_cover_image)
        val etTitle = view.findViewById<EditText>(R.id.et_edit_uploaded_textbook_details_title)
        val etAuthor = view.findViewById<EditText>(R.id.et_edit_uploaded_textbook_details_author)
        val etISBN = view.findViewById<EditText>(R.id.et_edit_uploaded_textbook_details_isbn)
        val etDescription =
            view.findViewById<EditText>(R.id.et_edit_uploaded_textbook_details_description)
        val etPrice = view.findViewById<EditText>(R.id.et_edit_uploaded_textbook_details_price)

        // Setting ViewModel
        viewModel = ViewModelProvider(this)[EditUploadedTextbookDetailsViewModel::class.java]

        // Binding image view for updates
        updatedUploadedImage = view.findViewById(R.id.iv_edit_uploaded_textbook_details_cover_image)

        // Displaying textbook details
        if (uploadedImageResId != null && uploadedImageResId != 0) {
            ivImage.setImageResource(uploadedImageResId)
        } else if (!uploadedImageUri.isNullOrEmpty()) {
            ivImage.setImageURI(uploadedImageUri.toUri())
        }

        tvUniversity.text = LoggedInStudent.university
        tvField.text = LoggedInStudent.field
        tvDegree.text = LoggedInStudent.degree
        etTitle.setText(title)
        etAuthor.setText(author)
        etISBN.setText(isbn?.toString())
        etDescription.setText(description)
        etPrice.setText("R%.2f".format(price ?: 0f))

        // Returning to previous screen
        ibBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Launching image picker (images)
        ibEdit.setOnClickListener {
            updatedUploadImage.launch(arrayOf("image/*"))
        }

        // Updating textbook details using functions
        btnSave.setOnClickListener {
            val uploadedImageResIdInput = null
            val uploadedImageUriInput =
                if (updatedUploadedImageUri != null) {
                    updatedUploadedImageUri
                } else {
                    uploadedImageUri?.toUri()
                }

            val isbnInputString = etISBN.text.toString()
            val isbnInput = isbnInputString.toLongOrNull()

            val titleInput = etTitle.text.toString().trim()
            val authorInput = etAuthor.text.toString().trim()
            val descriptionInput = etDescription.text.toString().trim()

            val titleLetterCount = titleInput.count { it.isLetter() }
            val authorLetterCount = authorInput.count { it.isLetter() }
            val descriptionLetterCount = descriptionInput.count { it.isLetter() }

            val priceInput = etPrice.text.toString().replace("R", "").trim().toFloatOrNull()

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
                    isbn!!,
                    uploadedImageResIdInput,
                    uploadedImageUriInput,
                    titleInput,
                    authorInput,
                    isbnInput!!,
                    descriptionInput,
                    priceInput!!
                )
            }
        }

        // Deleting textbook using function
        btnDelete.setOnClickListener {
            deleteTextbook(isbn!!)
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
            Toast.makeText(context, "Textbook updated.", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        } else {
            Toast.makeText(context, "Failed to update textbook.", Toast.LENGTH_SHORT).show()
        }
    }

    // Passing user input for deleting textbook
    fun deleteTextbook(currentISBN: Long) {
        val deleted = viewModel.delete(currentISBN)
        if (deleted) {
            Toast.makeText(context, "Textbook deleted.", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        } else {
            Toast.makeText(context, "Failed to delete textbook.", Toast.LENGTH_SHORT).show()
        }
    }
}