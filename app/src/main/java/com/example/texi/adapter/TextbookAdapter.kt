package com.example.texi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.texi.R
import com.example.texi.model.Textbook

class TextbookAdapter(
    private val textbooks: List<Textbook>
) : RecyclerView.Adapter<TextbookAdapter.TextbookViewHolder>() {

    class TextbookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivTextbook: ImageView = itemView.findViewById(R.id.iv_textbook)
        val btnTextbookDetails: Button = itemView.findViewById(R.id.btn_textbook_details)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextbookViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_textbook, parent, false)

        return TextbookViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextbookViewHolder, position: Int) {

        val textbook = textbooks[position]

        holder.ivTextbook.setImageResource(textbook.imageResId)
    }

    override fun getItemCount(): Int {
        return textbooks.size
    }
}