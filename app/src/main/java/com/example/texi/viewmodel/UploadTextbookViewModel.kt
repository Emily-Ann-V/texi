package com.example.texi.viewmodel

import androidx.lifecycle.ViewModel
import com.example.texi.model.uploadTextbook

class UploadTextbookViewModel: ViewModel() {
    fun upload(
        uploadedImageResIdInput: Int? = null,
        uploadedImageUriInput: String? = null,
        titleInput: String,
        authorInput: String,
        isbnInput: Long,
        descriptionInput: String,
        priceInput: Float,
        universityInput: String,
        fieldInput: String,
        degreeInput: String) : Boolean {

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
            degreeInput
        )
    }
}