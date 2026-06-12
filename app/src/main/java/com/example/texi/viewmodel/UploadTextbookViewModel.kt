package com.example.texi.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.texi.model.uploadTextbook

class UploadTextbookViewModel : ViewModel() {
    var uploadedImageResIdInput: Int? = null
    var uploadedImageUriInput: Uri? = null
    var titleInput: String = ""
    var authorInput: String = ""
    var isbnStringInput: String = ""
    var descriptionInput: String = ""
    var priceStringInput: String = ""
    var universityInput: String = ""
    var fieldInput: String = ""
    var degreeInput: String = ""
    var uploadedByInput: String = ""
    var errorMessage: String = ""

    // Passing user input to upload textbook (add to list of textbooks)
    fun upload(): Boolean {
        return if (validation()) {
            uploadTextbook(
                uploadedImageResIdInput,
                uploadedImageUriInput,
                titleInput,
                authorInput,
                isbnStringInput.toLongOrNull() ?: 0,
                descriptionInput,
                priceStringInput.toFloatOrNull() ?: 0f,
                universityInput,
                fieldInput,
                degreeInput,
                uploadedByInput
            )
        } else {
            false
        }
    }

    fun validation(): Boolean {

        val titleLetterCount = titleInput.count { it.isLetter() }
        val authorLetterCount = authorInput.count { it.isLetter() }
        val descriptionLetterCount = descriptionInput.count { it.isLetter() }

        if (uploadedImageUriInput == null) {
            errorMessage = "Please upload an image."
            return false
        }

        if (titleLetterCount < 5) {
            errorMessage = "Title must have 5+ letters."
            return false
        }

        if (authorLetterCount < 5) {
            errorMessage = "Author must have 5+ letters."
            return false
        }

        if (isbnStringInput.length != 13) {
            errorMessage = "ISBN must be 13 numbers."
            return false
        }

        if (descriptionLetterCount < 10) {
            errorMessage = "Description must have 10+ letters."
            return false
        }

        val priceInput = priceStringInput.toFloatOrNull()
        if (priceInput == null || (priceInput < 20f)) {
            errorMessage = "Price must be R20+."
            return false
        }

        errorMessage = ""
        return true
    }
}