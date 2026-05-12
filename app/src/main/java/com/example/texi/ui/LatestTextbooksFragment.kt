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

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_textbooks)
        val btnViewAllTextbooks =
            view.findViewById<Button>(R.id.btn_view_all_textbooks)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val textbookAdapter = TextbookAdapter(textbooks.takeLast(5))

        recyclerView.adapter = textbookAdapter

        btnViewAllTextbooks.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_main, AllTextbooksFragment())
                .commit()
        }
    }
}