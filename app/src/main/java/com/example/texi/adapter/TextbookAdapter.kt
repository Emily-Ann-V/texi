package com.example.texi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.texi.R
import com.example.texi.model.Textbook

// Adapter for RecyclerView items
class TextbookAdapter(
    private val textbooks: List<Textbook>,
    private val textbookDetailsOnClick: (Textbook) -> Unit
) : RecyclerView.Adapter<TextbookAdapter.TextbookViewHolder>() {

    class TextbookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivTextbook: ImageView = itemView.findViewById(R.id.iv_textbook_image)
        val btnTextbookDetails: Button = itemView.findViewById(R.id.btn_textbook_details_page)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextbookViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_textbook, parent, false)

        return TextbookViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextbookViewHolder, position: Int) {

        // Setting the textbook image cover
        val textbook = textbooks[position]
        val uploadedImageResId = textbook.uploadedImageResId

        if (uploadedImageResId != null && uploadedImageResId != 0) {
            holder.ivTextbook.setImageResource(uploadedImageResId)
        } else if (textbook.uploadedImageUri != null) {
            holder.ivTextbook.setImageURI(textbook.uploadedImageUri)
        } else {
            holder.ivTextbook.setImageResource(R.drawable.img_upload_textbook_image_preview_placeholder)
        }

        // Passing individual textbook for details screen
        holder.btnTextbookDetails.setOnClickListener {
            textbookDetailsOnClick(textbook)
        }
    }

    override fun getItemCount(): Int {
        return textbooks.size
    }
}