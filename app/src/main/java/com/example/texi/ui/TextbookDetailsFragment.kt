package com.example.texi.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.texi.R
import androidx.core.net.toUri

class TextbookDetailsFragment : Fragment(R.layout.fragment_textbook_details) {

    // Suppressing warning
    @SuppressLint("SetTextI18n")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieving textbook details from previous screen
        val uploadedImageResId = arguments?.getInt("uploadedImageResId")
        val uploadedImageUri = arguments?.getString("uploadedImageUri")
        val title = arguments?.getString("title")
        val author = arguments?.getString("author")
        val isbn = arguments?.getLong("isbn")
        val description = arguments?.getString("description")
        val price = arguments?.getFloat("price")
        val university = arguments?.getString("university")
        val field = arguments?.getString("field")
        val degree = arguments?.getString("degree")

        // Binding UI elements
        val ibBack = view.findViewById<ImageButton>(R.id.ib_textbook_details_back)
        val btnInquire = view.findViewById<Button>(R.id.btn_inquire_page)
        val ivImage = view.findViewById<ImageView>(R.id.iv_textbook_details_cover_image)
        val tvTitle = view.findViewById<TextView>(R.id.tv_textbook_details_title)
        val tvAuthor = view.findViewById<TextView>(R.id.tv_textbook_details_author)
        val tvISBN = view.findViewById<TextView>(R.id.tv_textbook_details_isbn)
        val tvDescription = view.findViewById<TextView>(R.id.tv_textbook_details_description)
        val tvPrice = view.findViewById<TextView>(R.id.tv_textbook_details_price)
        val tvUniversity = view.findViewById<TextView>(R.id.tv_textbook_details_university)
        val tvField = view.findViewById<TextView>(R.id.tv_textbook_details_field)
        val tvDegree = view.findViewById<TextView>(R.id.tv_textbook_details_degree)

        // Setting textbook image (resource or URI fallback)
        if (uploadedImageResId != null && uploadedImageResId != 0) {
            ivImage.setImageResource(uploadedImageResId)
        } else if (!uploadedImageUri.isNullOrEmpty()) {
            ivImage.setImageURI(uploadedImageUri.toUri())
        }

        // Displaying textbook details
        tvTitle.text = title
        tvAuthor.text = author
        tvISBN.text = isbn?.toString()
        tvDescription.text = description
        tvPrice.text = "R%.2f".format(price ?: 0f)
        tvUniversity.text = university
        tvField.text = field
        tvDegree.text = degree

        // Returning to previous screen
        ibBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Opening inquire screen
        btnInquire.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_main, InquireFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}