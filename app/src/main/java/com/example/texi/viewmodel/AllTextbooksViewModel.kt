package com.example.texi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.texi.model.Textbook
import com.example.texi.model.textbooks

class AllTextbooksViewModel : ViewModel() {

    // LiveData for observing textbook list changes
    private val _textbookList = MutableLiveData(textbooks.toList())
    val textbookList: LiveData<List<Textbook>> = _textbookList

    // Refreshing textbook list with current data from model
    fun refresh() {
        _textbookList.value = textbooks.toList()
    }
}