package com.example.texi.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.texi.model.deleteTextbook
import com.example.texi.model.updateTextbook
import com.example.texi.model.uploadTextbook

class EditUploadedTextbookDetailsViewModel: ViewModel() {
    fun update(
        currentISBN: Long,
        uploadedImageResIdInput: Int? = null,
        uploadedImageUriInput: Uri? = null,
        titleInput: String,
        authorInput: String,
        isbnInput: Long,
        descriptionInput: String,
        priceInput: Float
    ) : Boolean {

        return updateTextbook(
            currentISBN,
            uploadedImageResIdInput,
            uploadedImageUriInput,
            titleInput,
            authorInput,
            isbnInput,
            descriptionInput,
            priceInput
        )
    }

    fun delete(currentISBN: Long):Boolean{
        return deleteTextbook(currentISBN)
    }
}