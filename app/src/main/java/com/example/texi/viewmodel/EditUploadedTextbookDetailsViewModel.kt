package com.example.texi.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.texi.model.deleteTextbook
import com.example.texi.model.updateTextbook

class EditUploadedTextbookDetailsViewModel : ViewModel() {

    // Two way data binding variables
    var uploadedImageResIdInput: Int? = null
    var uploadedImageUriInput: Uri? = null
    var titleInput: String = ""
    var authorInput: String = ""
    var isbnStringInput: String = ""
    var descriptionInput: String = ""
    var priceStringInput: String = ""
    var errorMessage: String = ""

    // Passing user input to update textbook details
    fun update(currentISBN: Long): Boolean {

        // Updating if validation is successful
        return if (validation()) {
            updateTextbook(
                currentISBN,
                uploadedImageResIdInput,
                uploadedImageUriInput,
                titleInput,
                authorInput,
                isbnStringInput.toLongOrNull() ?: 0,
                descriptionInput,
                priceStringInput.toFloatOrNull() ?: 0f
            )
        } else {
            false
        }
    }

    // Passing ISBN number to delete textbook
    fun delete(currentISBN: Long): Boolean {
        return deleteTextbook(currentISBN)
    }

    // Helper function to validate user input
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