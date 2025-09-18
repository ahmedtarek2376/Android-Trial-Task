package com.palm.trial.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.palm.trial.domain.model.ChatMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

object MessagesRepo {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val _messages = MutableLiveData<List<ChatMessage>>()
    val messages: LiveData<List<ChatMessage>> = _messages

    init {
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