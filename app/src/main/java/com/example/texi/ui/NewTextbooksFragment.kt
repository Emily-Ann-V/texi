package com.example.texi.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.texi.R
import com.example.texi.adapter.TextbookAdapter
import com.example.texi.model.textbooks

class NewTextbooksFragment : Fragment(R.layout.fragment_new_textbooks) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_textbooks)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val textbookAdapter = TextbookAdapter(textbooks.take(5))

        recyclerView.adapter = textbookAdapter
    }
}