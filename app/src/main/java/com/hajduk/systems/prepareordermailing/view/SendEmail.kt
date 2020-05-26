package com.hajduk.systems.prepareordermailing.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.hajduk.systems.prepareordermailing.R
import com.hajduk.systems.prepareordermailing.adapter.email.EmailData
import com.hajduk.systems.prepareordermailing.adapter.email.IntentEmailSender
import com.hajduk.systems.prepareordermailing.holder.DomainDataHolder
import com.hajduk.systems.prepareordermailing.service.template.PrepareOrderEmailRenderData
import com.hajduk.systems.prepareordermailing.service.template.TemplateEngine

class SendEmail : AppCompatActivity() {

    private val emailSender = IntentEmailSender()
    private val templateEngine = TemplateEngine()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_email)
        findViewById<Button>(R.id.restartFlowButton).setOnClickListener { startActivity(Intent(this, ScanBarCode::class.java)) }
        emailSender.sentEmail(resolveEmailData(), this)
    }

    private fun resolveEmailData(): EmailData {
        val templateData = PrepareOrderEmailRenderData(DomainDataHolder.demandCustomer(), DomainDataHolder.demandOrder())
        return EmailData(
            templateData.customer.email,
            "Aktualizacja zamówienia: ${templateData.order.id}",
            templateEngine.renderPrepareOrderEmail(templateData),
            listOf(Uri.fromFile(DomainDataHolder.demandPhotoFile()))
        )
    }
}