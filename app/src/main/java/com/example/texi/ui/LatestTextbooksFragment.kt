package com.example.texi.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.texi.R
import com.example.texi.adapter.TextbookAdapter
import com.example.texi.model.textbooks

class LatestTextbooksFragment : Fragment(R.layout.fragment_latest_textbooks) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_latest_textbooks)
        val btnViewAllTextbooks =
            view.findViewById<Button>(R.id.btn_view_all_textbooks_page)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val latestTextbookAdapter = TextbookAdapter(textbooks.takeLast(5)) { textbook ->

            val bundle = Bundle().apply {
                if (textbook.uploadedImageResId != null) {
                    putInt("uploadedImageResId", textbook.uploadedImageResId)
                }
                putString("uploadedImageUri", textbook.uploadedImageUri)
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

        recyclerView.adapter = latestTextbookAdapter

        btnViewAllTextbooks.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_main, AllTextbooksFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}