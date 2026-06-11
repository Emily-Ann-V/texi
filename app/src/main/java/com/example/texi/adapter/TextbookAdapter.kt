package com.example.texi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.texi.R
import com.example.texi.databinding.ItemTextbookBinding
import com.example.texi.model.Textbook

// Adapter for RecyclerView items
class TextbookAdapter(
    private var textbooks: List<Textbook>,
    private val textbookButtonText: String = "Details",
    private val textbookDetailsOnClick: (Textbook) -> Unit
) : RecyclerView.Adapter<TextbookAdapter.TextbookViewHolder>() {

    class TextbookViewHolder(val binding: ItemTextbookBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextbookViewHolder {

        val binding = ItemTextbookBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return TextbookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextbookViewHolder, position: Int) {

        val textbook = textbooks[position]

        // Setting the textbook image cover
        val uploadedImageResId = textbook.uploadedImageResId

        if (uploadedImageResId != null && uploadedImageResId != 0) {
            holder.binding.ivTextbookImage.setImageResource(uploadedImageResId)
        } else if (textbook.uploadedImageUri != null) {
            holder.binding.ivTextbookImage.setImageURI(textbook.uploadedImageUri)
        } else {
            holder.binding.ivTextbookImage.setImageResource(R.drawable.img_default_logo)
        }

        // Setting button text for the current screen
        holder.binding.btnTextbookDetailsPage.text = textbookButtonText

        // Passing individual textbook for details screen
        holder.binding.btnTextbookDetailsPage.setOnClickListener {
            textbookDetailsOnClick(textbook)
        }
    }

    override fun getItemCount(): Int {
        return textbooks.size
    }

    fun updateList(newList: List<Textbook>) {
        textbooks = newList
        notifyDataSetChanged()
    }
}