package com.example.texi.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.texi.R
import com.example.texi.adapter.TextbookAdapter
import com.example.texi.model.textbooks

class LatestTextbooksFragment : Fragment(R.layout.fragment_latest_textbooks) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Binding UI elements
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_latest_textbooks)
        val btnViewAllTextbooks = view.findViewById<Button>(R.id.btn_view_all_textbooks_page)
        val tvNoBooksMessage = view.findViewById<TextView>(
            R.id.tv_latest_textbooks_no_books_message
        )

        // Getting latest uploaded textbooks
        val mlLatestTextbooks = textbooks.takeLast(5)

        // Setting up RecyclerView layout
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Showing TextView message textbooks are not available
        if (mlLatestTextbooks.isEmpty()) {
            tvNoBooksMessage.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            tvNoBooksMessage.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        // Setting adapter for latest textbooks
        val latestTextbookAdapter = TextbookAdapter(mlLatestTextbooks) { textbook ->

            // Passing selected textbook details to details screen
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

            // Opening textbook details screen
            val fragment = TextbookDetailsFragment()
            fragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_main, fragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = latestTextbookAdapter

        // Opening all textbooks screen
        btnViewAllTextbooks.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_main, AllTextbooksFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}