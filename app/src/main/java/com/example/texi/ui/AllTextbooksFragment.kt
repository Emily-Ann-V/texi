package com.example.texi.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.texi.R
import com.example.texi.adapter.TextbookAdapter
import com.example.texi.viewmodel.AllTextbooksViewModel

class AllTextbooksFragment : Fragment(R.layout.fragment_all_textbooks) {

    // Setting ViewModel and RecyclerView components
    private lateinit var viewModel: AllTextbooksViewModel
    private lateinit var allTextbookAdapter: TextbookAdapter
    private var allTextbooksList = listOf<com.example.texi.model.Textbook>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Binding UI elements
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_all_textbooks)
        val ibSearchTextbooks = view.findViewById<ImageButton>(R.id.ib_all_textbooks_search_icon)
        val ibFilterTextbooks = view.findViewById<ImageButton>(R.id.ib_all_textbooks_filter_icon)
        val etSearchBar = view.findViewById<EditText>(R.id.et_all_textbooks_search_bar)
        val tvNoBooksMessage = view.findViewById<TextView>(R.id.tv_all_textbooks_no_books_message)

        // Setting ViewModel
        viewModel = ViewModelProvider(requireActivity())[AllTextbooksViewModel::class.java]

        // Setting RecyclerView layout
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Setting adapter for all textbooks
        allTextbookAdapter = TextbookAdapter(emptyList()) { textbook ->
            openTextbookDetails(textbook)
        }
        recyclerView.adapter = allTextbookAdapter

        // Observing data to update UI dynamically
        viewModel.textbookList.observe(viewLifecycleOwner) { updatedList ->
            allTextbooksList = updatedList
            allTextbookAdapter.updateList(updatedList)
            updateEmptyState(recyclerView, tvNoBooksMessage, updatedList)
        }

        // Showing empty state message when textbook list is empty
        updateEmptyState(recyclerView, tvNoBooksMessage, allTextbooksList)

        // Applying filter listener for textbooks
        filterTextbooks(recyclerView, tvNoBooksMessage)

        // Searching for textbooks using function
        ibSearchTextbooks.setOnClickListener {
            val searchBarInput = etSearchBar.text.toString()
            searchTextbooks(recyclerView, searchBarInput, tvNoBooksMessage)
        }

        // Opening filter screen
        ibFilterTextbooks.setOnClickListener {
            loadNewFragment(FilterTextbooksFragment())
        }
    }

    // Opening textbook details screen and passing selected textbook data
    private fun openTextbookDetails(textbook: com.example.texi.model.Textbook) {

        val fragment = TextbookDetailsFragment().apply {
            arguments = Bundle().apply {
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
        }

        loadNewFragment(fragment)
    }

    // Searching textbooks by title or author
    fun searchTextbooks(
        recyclerView: RecyclerView,
        searchBarInput: String,
        tvNoBooksMessage: TextView
    ) {

        val searchedTextbooks = allTextbooksList.filter { textbook ->
            textbook.title.contains(searchBarInput, true)
                    || textbook.author.contains(searchBarInput, true)
        }

        // Updating textbook list
        allTextbookAdapter.updateList(searchedTextbooks)

        // Showing empty state message when textbook list is empty
        updateEmptyState(recyclerView, tvNoBooksMessage, searchedTextbooks)
    }

    // Filtering textbooks using filterKey from FilterTextbooksFragment
    fun filterTextbooks(recyclerView: RecyclerView, tvNoBooksMessage: TextView) {

        parentFragmentManager.setFragmentResultListener(
            "filterKey",
            viewLifecycleOwner
        ) { _, bundle ->

            val university = bundle.getString("university") ?: "Any"
            val field = bundle.getString("field") ?: "Any"
            val degree = bundle.getString("degree") ?: "Any"

            val filteredTextbooks = allTextbooksList.filter { textbook ->

                (university == "Any"
                        || textbook.university.equals(university, true)) &&
                        (field == "Any"
                                || textbook.field.equals(field, true)) &&
                        (degree == "Any"
                                || textbook.degree.equals(degree, true))
            }

            // Updating textbook list
            allTextbookAdapter.updateList(filteredTextbooks)

            // Showing empty state message when textbook list is empty
            updateEmptyState(recyclerView, tvNoBooksMessage, filteredTextbooks)
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

    // Helper function for fragment navigation
    fun loadNewFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fl_main, fragment)
            .addToBackStack(null)
            .commit()
    }
}