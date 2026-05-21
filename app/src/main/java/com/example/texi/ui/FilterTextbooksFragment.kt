package com.example.texi.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.texi.R
import com.example.texi.model.textbooks

class FilterTextbooksFragment : Fragment(R.layout.fragment_filter_textbooks) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Binding UI elements
        val ibCloseFilter = view.findViewById<ImageButton>(
            R.id.ib_filter_textbooks_close_filter_options_icon
        )
        val btnFilter = view.findViewById<Button>(R.id.btn_filter_textbooks_submit)
        val spUniversity = view.findViewById<Spinner>(R.id.sp_filter_textbooks_university)
        val spField = view.findViewById<Spinner>(R.id.sp_filter_textbooks_field)
        val spDegree = view.findViewById<Spinner>(R.id.sp_filter_textbooks_degree)

        // Setting spinner options
        setSpinnerOptions(spUniversity, spField, spDegree)

        // Returning to previous page
        ibCloseFilter.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Filtering textbooks and passing data
        btnFilter.setOnClickListener {

            val bundle = Bundle().apply {
                putString("university", spUniversity.selectedItem.toString())
                putString("field", spField.selectedItem.toString())
                putString("degree", spDegree.selectedItem.toString())
            }

            parentFragmentManager.setFragmentResult("filterKey", bundle)
            parentFragmentManager.popBackStack()
        }
    }

    // Setting spinner options from available textbooks
    private fun setSpinnerOptions(
        spUniversity: Spinner,
        spField: Spinner,
        spDegree: Spinner
    ) {

        val universityOptions = mutableListOf("Any").apply {
            addAll(textbooks.map { it.university }.distinct())
        }

        val fieldOptions = mutableListOf("Any").apply {
            addAll(textbooks.map { it.field }.distinct())
        }

        val degreeOptions = mutableListOf("Any").apply {
            addAll(textbooks.map { it.degree }.distinct())
        }

        // Setting spinner adapters
        val universityAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_filter_spinner,
            universityOptions
        )

        val fieldAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_filter_spinner,
            fieldOptions
        )

        val degreeAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_filter_spinner,
            degreeOptions
        )

        universityAdapter.setDropDownViewResource(R.layout.item_filter_spinner)
        fieldAdapter.setDropDownViewResource(R.layout.item_filter_spinner)
        degreeAdapter.setDropDownViewResource(R.layout.item_filter_spinner)

        spUniversity.adapter = universityAdapter
        spField.adapter = fieldAdapter
        spDegree.adapter = degreeAdapter
    }
}