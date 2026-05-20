package com.example.texi.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.texi.R
import com.example.texi.adapter.TextbookAdapter
import com.example.texi.model.LoggedInStudent
import com.example.texi.model.textbooks

class MyUploadsFragment: Fragment(R.layout.fragment_my_uploads) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_my_uploads)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val uploadedTextbookAdapter = TextbookAdapter(textbooks.filter { it.uploadedBy == LoggedInStudent.emailAddress }) { textbook ->

            val bundle = Bundle().apply {
                if (textbook.uploadedImageResId != null) {
                    putInt("uploadedImageResId", textbook.uploadedImageResId)
                }
                putString("uploadedImageUri", textbook.uploadedImageUri.toString())
                putString("title", textbook.title)
                putString("author", textbook.author)
                putLong("isbn", textbook.isbn)
                putString("description", textbook.description)
                putFloat("price", textbook.price)
                putString("field", textbook.field)
                putString("university", textbook.university)
                putString("degree", textbook.degree)
            }

            val fragment = TextbookDetailsFragment()
            fragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_main, fragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = uploadedTextbookAdapter
    }
}