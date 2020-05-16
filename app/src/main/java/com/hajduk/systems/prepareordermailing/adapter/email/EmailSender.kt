package com.hajduk.systems.prepareordermailing.adapter.email

import android.content.Context
import android.net.Uri

interface EmailSender {
    fun sentEmail(emailData: EmailData, context: Context): EmailSendResult
}

data class EmailData(
    val receiver: String,
    val subject: String,
    val bodyText: String,
    val attachments: List<Uri> = ArrayList()
)

data class EmailSendResult(val errorMessage: String? = null) {

    fun isSuccess(): Boolean {
        return errorMessage != null
    }
}