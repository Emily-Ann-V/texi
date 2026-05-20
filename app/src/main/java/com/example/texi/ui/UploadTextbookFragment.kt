package com.example.texi.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.texi.R
import com.example.texi.model.LoggedInStudent
import com.example.texi.viewmodel.UploadTextbookViewModel

class UploadTextbookFragment: Fragment(R.layout.fragment_upload_textbook) {

    private lateinit var viewModel: UploadTextbookViewModel
    private lateinit var uploadedImage: ImageView
    private var uploadedImageUri : Uri? = null
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
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[UploadTextbookViewModel::class.java]

        uploadedImage = view.findViewById<ImageView>(R.id.iv_upload_textbook_image)
        val ibUploadImage = view.findViewById<ImageButton>(R.id.ib_upload_textbook_upload_image_icon)
        val btnUpload = view.findViewById<Button>(R.id.btn_upload_textbook_submit)
        val etTitle = view.findViewById<EditText>(R.id.et_upload_textbook_title)
        val etAuthor = view.findViewById<EditText>(R.id.et_upload_textbook_author)
        val etDescription = view.findViewById<EditText>(R.id.et_upload_textbook_description)
        val etISBN = view.findViewById<EditText>(R.id.et_upload_textbook_isbn)
        val etPrice = view.findViewById<EditText>(R.id.et_upload_textbook_price)

        ibUploadImage.setOnClickListener {
            uploadImage.launch(arrayOf("image/*"))
        }

        btnUpload.setOnClickListener {
            val uploadedImageResIdInput = null
            val uploadedImageUriInput = uploadedImageUri

            val titleInput = etTitle.text.toString().trim()
            val authorInput = etAuthor.text.toString().trim()
            val descriptionInput = etDescription.text.toString().trim()

            val titleLetterCount = titleInput.count { it.isLetter() }
            val authorLetterCount = authorInput.count { it.isLetter() }
            val descriptionLetterCount = descriptionInput.count { it.isLetter() }

            val isbnInputString = etISBN.text.toString()
            val isbnInput = isbnInputString.toLongOrNull()
            val priceInput = etPrice.text.toString().toFloatOrNull()

            val universityInput = LoggedInStudent.university
            val fieldInput = LoggedInStudent.field
            val degreeInput = LoggedInStudent.degree
            val uploadedByInput = LoggedInStudent.emailAddress

           if (uploadValidation(uploadedImageUriInput,titleLetterCount,authorLetterCount,isbnInputString,descriptionLetterCount,priceInput)){
           uploadTextbook(uploadedImageResIdInput,uploadedImageUriInput,titleInput,authorInput,isbnInput!!,descriptionInput,priceInput!!, universityInput,fieldInput,degreeInput,uploadedByInput)

                }
        }
    }

    fun uploadValidation(
        uploadedImageUriInput: Uri? = null,
        titleLetterCount: Int,
        authorLetterCount: Int,
        isbnInputString: String,
        descriptionLetterCount: Int,
        priceInput: Float?,
    ): Boolean{

        if(uploadedImageUriInput == null){
            Toast.makeText(context, "Please upload an image.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (titleLetterCount < 5){
            Toast.makeText(context, "Title must have 5+ letters.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (authorLetterCount < 5){
            Toast.makeText(context, "Author must have 5+ letters.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (isbnInputString.length != 13 ){
            Toast.makeText(context, "ISBN must be 13 numbers.", Toast.LENGTH_SHORT).show()
            return false
        }

        if(descriptionLetterCount < 10){
            Toast.makeText(context, "Description must have 10+ letters.", Toast.LENGTH_SHORT).show()
            return false
        }

        if(priceInput != null && priceInput < 20f){
            Toast.makeText(context, "Price must be R20+.", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    fun uploadTextbook(
        uploadedImageResIdInput: Int? = null,
        uploadedImageUriInput: Uri? = null,
        titleInput: String,
        authorInput: String,
        isbnInput: Long,
        descriptionInput: String,
        priceInput: Float,
        universityInput: String,
        fieldInput: String,
        degreeInput: String,
        uploadedByInput: String) {

        val uploaded =  viewModel.upload(
            uploadedImageResIdInput,
            uploadedImageUriInput,
            titleInput,
            authorInput,
            isbnInput,
            descriptionInput,
            priceInput,
            universityInput,
            fieldInput,
            degreeInput,
            uploadedByInput
        )

        if (uploaded){
            Toast.makeText(context, "Textbook uploaded.", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        } else {
            Toast.makeText(context, "Failed to upload textbook.", Toast.LENGTH_SHORT).show()
        }
    }
}