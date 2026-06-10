package com.example.texi.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.texi.R
import com.example.texi.adapter.TextbookAdapter
import com.example.texi.model.LoggedInStudent
import com.example.texi.model.textbooks

class MyUploadsFragment : Fragment(R.layout.fragment_my_uploads) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Binding UI elements
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_my_uploads)
        val tvNoBooksMessage = view.findViewById<TextView>(R.id.tv_my_uploads_no_books_message)

        // Getting uploaded textbooks for logged in user
        val uploadedTextbooks = textbooks.filter {
            it.uploadedBy == LoggedInStudent.emailAddress
        }

        // Setting RecyclerView layout
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Showing empty state message when textbook list is empty
        if (uploadedTextbooks.isEmpty()) {

            tvNoBooksMessage.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE

        } else {

            tvNoBooksMessage.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        // Setting adapter for uploaded textbooks
        val uploadedTextbookAdapter = TextbookAdapter(uploadedTextbooks,
            "Edit") { textbook ->

            // Passing selected textbook details to edit screen
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

            // Opening edit textbook screen
            val fragment = EditUploadedTextbookDetailsFragment()
            fragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_main, fragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = uploadedTextbookAdapter
    }
}