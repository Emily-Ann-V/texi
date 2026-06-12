package com.example.texi.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.texi.R
import com.example.texi.databinding.FragmentTextbookDetailsBinding

class TextbookDetailsFragment : Fragment(R.layout.fragment_textbook_details) {

    // Setting Binding components
    private lateinit var binding: FragmentTextbookDetailsBinding

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

        // Create textbook object from arguments
        val textbook = com.example.texi.model.Textbook(
            uploadedImageResId = uploadedImageResId,
            uploadedImageUri = uploadedImageUri?.toUri(),
            title = title ?: "",
            author = author ?: "",
            isbn = isbn ?: 0,
            description = description ?: "",
            price = price ?: 0f,
            university = university ?: "",
            field = field ?: "",
            degree = degree ?: "",
            uploadedBy = ""
        )

        // Initialising binding
        binding = FragmentTextbookDetailsBinding.bind(view)

        // Bind textbook to layout
        binding.textbook = textbook

        // Setting textbook image (resource or URI fallback)
        if (uploadedImageResId != null && uploadedImageResId != 0) {
            binding.ivTextbookDetailsCoverImage.setImageResource(uploadedImageResId)
        } else if (!uploadedImageUri.isNullOrEmpty()) {
            binding.ivTextbookDetailsCoverImage.setImageURI(uploadedImageUri.toUri())
        }

        // Executing pending bindings
        binding.executePendingBindings()

        // Returning to previous screen
        binding.ibTextbookDetailsBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Opening inquire screen
        binding.btnInquirePage.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fl_main, InquireFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}