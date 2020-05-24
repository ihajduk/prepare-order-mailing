package com.hajduk.systems.prepareordermailing.service.template

import com.hajduk.systems.prepareordermailing.adapter.woocommerce.model.CustomerDto
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.model.OrderDto
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TemplateEngine {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun renderPrepareOrderEmail(templateData: PrepareOrderEmailRenderData): String {
        val lineItems = templateData.order.lineItems.map { lineItem -> "  - \"${lineItem.name}\" - ${lineItem.quantity} szt." }.joinToString("\n")
        return """Witaj ${templateData.customer.firstName}!
Twoje zamówienie nr. ${templateData.order.id}  
o wartości ${formatCurrency(templateData.order.total)} 
jest właśnie pakowane i już za chwilę zostanie przekazane kurierowi.
Pozycje zamówienia: 
$lineItems
Pozdrawiamy!"""
    }

    private fun formatDate(date: LocalDateTime): String {
        return formatter.format(date)
    }

    private fun formatCurrency(value: BigDecimal): String {
        return value.toString().replace(".", ",") + " zł"
    }
}

data class PrepareOrderEmailRenderData(
    val customer: CustomerDto,
    val order: OrderDto
)