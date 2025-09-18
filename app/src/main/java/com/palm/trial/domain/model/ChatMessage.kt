package com.palm.trial.domain.model

import java.util.UUID

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val message: String,
    val date: String
)