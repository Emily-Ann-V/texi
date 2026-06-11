package com.example.texi.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.texi.R
import com.example.texi.adapter.TextbookAdapter
import com.example.texi.viewmodel.AllTextbooksViewModel

class LatestTextbooksFragment : Fragment(R.layout.fragment_latest_textbooks) {

    private lateinit var viewModel: AllTextbooksViewModel
    private var latestTextbooksList = listOf<com.example.texi.model.Textbook>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Binding UI elements
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_latest_textbooks)
        val btnViewAllTextbooks = view.findViewById<Button>(R.id.btn_view_all_textbooks_page)
        val tvNoBooksMessage = view.findViewById<TextView>(
            R.id.tv_latest_textbooks_no_books_message
        )

        // Setting ViewModel
        viewModel = ViewModelProvider(requireActivity())[AllTextbooksViewModel::class.java]

        // Setting up RecyclerView layout
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Showing empty state message when textbook list is empty
        updateEmptyState(recyclerView, tvNoBooksMessage, latestTextbooksList)

        // Setting adapter for latest textbooks
        val latestTextbookAdapter = TextbookAdapter(emptyList()) { textbook ->

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

        // Observing data
        viewModel.textbookList.observe(viewLifecycleOwner) { fullList ->
            latestTextbooksList = fullList.takeLast(5)
            latestTextbookAdapter.updateList(latestTextbooksList)
            updateEmptyState(recyclerView, tvNoBooksMessage, latestTextbooksList)
        }

        // Opening all textbooks screen
        btnViewAllTextbooks.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_main, AllTextbooksFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    // Helper function to show empty state message when textbook list is empty
    private fun updateEmptyState(
        recyclerView: RecyclerView,
        tvNoBooksMessage: TextView,
        currentList: List<com.example.texi.model.Textbook>
    ) {
        val isEmpty = currentList.isEmpty()

        tvNoBooksMessage.visibility = if (isEmpty) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }
}