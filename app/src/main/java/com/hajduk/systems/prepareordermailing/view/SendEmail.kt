package com.hajduk.systems.prepareordermailing.view

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hajduk.systems.prepareordermailing.ORDER_ID
import com.hajduk.systems.prepareordermailing.PHOTO_ABSOLUTE_PATH
import com.hajduk.systems.prepareordermailing.R
import com.hajduk.systems.prepareordermailing.adapter.email.EmailData
import com.hajduk.systems.prepareordermailing.adapter.email.IntentEmailSender
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.model.CustomerDto
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.model.LineItemDto
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.model.OrderDto
import com.hajduk.systems.prepareordermailing.service.template.PrepareOrderEmailRenderData
import com.hajduk.systems.prepareordermailing.service.template.TemplateEngine
import kotlinx.android.synthetic.main.activity_send_email.*
import java.io.File
import java.time.LocalDateTime

class SendEmail : AppCompatActivity() {

    private val emailSender = IntentEmailSender()
    private val templateEngine = TemplateEngine()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_email)
        val emailData = resolveEmailData()
        sendEmailTextView.text = "Wysyłam email do: ${emailData.receiver}"
        emailSender.sentEmail(emailData, this)
    }

    private fun resolveEmailData(): EmailData {
        val templateData = getTestData()
        return EmailData(
            templateData.customer.email,
            "Aktualizacja zamówienia: ${templateData.order.id}",
            templateEngine.renderPrepareOrderEmail(templateData),
            listOf(Uri.fromFile(getPhotoFile()))
        )
    }

    private fun getPhotoFile(): File {
        val photoFile = File(intent.getStringExtra(PHOTO_ABSOLUTE_PATH) ?: throw IllegalStateException("Photo file path is missing"))
        if (!photoFile.exists()) {
            throw IllegalStateException("Photo file doesn't exist")
        } else {
            return photoFile
        }
    }

    private fun getTestData(): PrepareOrderEmailRenderData {
        val customer = CustomerDto(
            1,
            "hajduk.nieruchomosci+email_app@gmail.com",
            "Patryk",
            "Hajduk",
            "username"
        )
        val order = OrderDto(
            getOrderId().toInt(),
            LocalDateTime.now(),
            1,
            10.11.toBigDecimal(),
            listOf(LineItemDto(1, "Product 1", 1), LineItemDto(2, "Product 2", 2))
        )
        return PrepareOrderEmailRenderData(customer, order)
    }

    private fun getOrderId(): String {
        return intent.getStringExtra(ORDER_ID) ?: throw IllegalStateException("Order id is missing")
    }
}