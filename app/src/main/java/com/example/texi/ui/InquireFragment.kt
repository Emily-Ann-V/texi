package com.example.texi.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.texi.R

class InquireFragment : Fragment(R.layout.fragment_inquire) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Binding UI elements
        val ibSendMessage = view.findViewById<ImageButton>(R.id.ib_inquire_send_message_icon)
        val etType = view.findViewById<EditText>(R.id.et_inquire_type)

        // Displaying given message on inquire screen
        ibSendMessage.setOnClickListener {

            val typeInput = etType.text.toString().trim()
            val tvMessage = view.findViewById<TextView>(R.id.tv_inquire_message)

            // Displaying the message when input is not empty
            if (typeInput.isNotEmpty()) {

                tvMessage.text = typeInput
                tvMessage.visibility = View.VISIBLE

            } else {
                Toast.makeText(
                    requireContext(),
                    "Please type a message.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}