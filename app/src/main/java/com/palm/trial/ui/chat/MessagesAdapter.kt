package com.palm.trial.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.palm.trial.R
import com.palm.trial.domain.model.ChatMessage

class MessagesAdapter(private val messages: List<ChatMessage>) :
    RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    override fun getItemCount() = messages.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.messageTextView.text = message.message
        holder.dateTextView.text = message.date
    }

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageTextView: TextView = view.findViewById(R.id.text_message)
        val dateTextView: TextView = view.findViewById(R.id.text_date)
    }
}