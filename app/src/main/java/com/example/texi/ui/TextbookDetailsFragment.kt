package com.example.texi.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.texi.R

class TextbookDetailsFragment : Fragment(R.layout.fragment_textbook_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageResId = arguments?.getInt("imageResId")
        val title = arguments?.getString("title")
        val author = arguments?.getString("author")
        val isbn = arguments?.getLong("isbn")
        val description = arguments?.getString("description")
        val price = arguments?.getFloat("price")
        val university = arguments?.getString("university")
        val field = arguments?.getString("field")
        val degree = arguments?.getString("degree")

        val btnInquire = view.findViewById<Button>(R.id.btn_inquire_page)
        val ivImage = view.findViewById<ImageView>(R.id.iv_textbook_details_image)
        val tvTitle = view.findViewById<TextView>(R.id.tv_textbook_details_title)
        val tvAuthor = view.findViewById<TextView>(R.id.tv_textbook_details_author)
        val tvISBN = view.findViewById<TextView>(R.id.tv_textbook_details_isbn)
        val tvDescription = view.findViewById<TextView>(R.id.tv_textbook_details_description)
        val tvPrice = view.findViewById<TextView>(R.id.tv_textbook_details_price)
        val tvUniversity = view.findViewById<TextView>(R.id.tv_textbook_details_university)
        val tvField = view.findViewById<TextView>(R.id.tv_textbook_details_field)
        val tvDegree = view.findViewById<TextView>(R.id.tv_textbook_details_degree)

        btnInquire.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_main, InquireFragment())
                .addToBackStack(null)
                .commit()
        }

        ivImage.setImageResource(imageResId ?: 0)
        tvTitle.text = title
        tvAuthor.text = author
        tvISBN.text = isbn?.toString()
        tvDescription.text = description
        tvPrice.text = "R%.2f".format(price ?: 0f)
        tvUniversity.text = university
        tvField.text = field
        tvDegree.text = degree
    }
}