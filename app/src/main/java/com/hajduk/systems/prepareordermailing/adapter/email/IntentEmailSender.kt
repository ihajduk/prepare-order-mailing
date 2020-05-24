package com.hajduk.systems.prepareordermailing.adapter.email

import android.content.Context
import android.content.Intent
import android.net.Uri

class IntentEmailSender : EmailSender {

    override fun sentEmail(emailData: EmailData, context: Context): EmailSendResult {
        val mailIntent = buildMailIntent(emailData)
        return if (mailIntent.resolveActivity(context.packageManager) == null) {
            EmailSendResult("No email app available")
        } else {
            context.startActivity(mailIntent)
            EmailSendResult()
        }
    }

    private fun buildMailIntent(emailData: EmailData): Intent {
        return Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${emailData.receiver}")
            putExtra(Intent.EXTRA_EMAIL, emailData.receiver)
            putExtra(Intent.EXTRA_SUBJECT, emailData.subject)
            putExtra(Intent.EXTRA_TEXT, emailData.bodyText)
            emailData.attachments.forEach { attachment -> putExtra(Intent.EXTRA_STREAM, attachment) }
        }
    }
}