package com.palm.trial.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.palm.trial.domain.model.ChatMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

object MessagesRepo {
    private var coroutineScope = CoroutineScope(Dispatchers.Main)

    private val _messages = MutableLiveData<List<ChatMessage>>()
    val messages: LiveData<List<ChatMessage>> = _messages

    // For testing to allow scope control
    fun resetScope() {
        coroutineScope.cancel()
        coroutineScope = CoroutineScope(Dispatchers.Main)
        _messages.value = emptyList()
    }

    init {
        startMessageUpdates()
    }

    private fun startMessageUpdates() {
        coroutineScope.launch {
            repeat(100) {
                _messages.value = _messages.value.orEmpty()
                    .plus(
                        ChatMessage(
                            message = "This is a dummy message",
                            date = Date().toString()
                        )
                    )
                delay(2000)
            }
        }
    }
}