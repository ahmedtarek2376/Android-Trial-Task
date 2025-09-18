package com.palm.trial.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.palm.trial.data.repo.MessagesRepo
import com.palm.trial.domain.model.ChatMessage

class ChatViewModel : ViewModel() {
    val messages: LiveData<List<ChatMessage>> = MessagesRepo.messages
}