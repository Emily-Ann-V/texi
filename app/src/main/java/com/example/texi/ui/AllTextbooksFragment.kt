package com.example.texi.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.texi.R
import com.example.texi.adapter.TextbookAdapter
import com.example.texi.model.textbooks

class AllTextbooksFragment : Fragment(R.layout.fragment_all_textbooks){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_all_textbooks)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val allTextbookAdapter = TextbookAdapter(textbooks) { textbook ->

            val bundle = Bundle()

            bundle.putInt("imageResId", textbook.imageResId)
            bundle.putString("title", textbook.title)
            bundle.putString("author", textbook.author)
            bundle.putLong("isbn", textbook.isbn)
            bundle.putString("description", textbook.description)
            bundle.putFloat("price", textbook.price)
            bundle.putString("field", textbook.field)
            bundle.putString("university", textbook.university)
            bundle.putString("degree", textbook.degree)

            val fragment = TextbookDetailsFragment()
            fragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_main, fragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = allTextbookAdapter
    }
}