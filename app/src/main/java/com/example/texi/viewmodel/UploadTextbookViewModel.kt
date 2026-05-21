package com.example.texi.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.texi.model.uploadTextbook

class UploadTextbookViewModel : ViewModel() {

    // Passing user input to upload textbook (add to list of textbooks)
    fun upload(
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
        uploadedBy: String
    ): Boolean {

        return uploadTextbook(
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
            uploadedBy
        )
    }
}