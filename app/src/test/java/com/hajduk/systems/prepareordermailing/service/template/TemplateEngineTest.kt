package com.hajduk.systems.prepareordermailing.service.template

import com.hajduk.systems.prepareordermailing.adapter.woocommerce.model.CustomerDto
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.model.LineItemDto
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.model.OrderDto
import org.junit.Assert
import org.junit.Test

class TemplateEngineTest {

    private val templateEngine = TemplateEngine()

    @Test
    fun should_render_prepare_order_email() {
        //given
        val customer = CustomerDto(
            1,
            "hajduk.nieruchomosci+email_app@gmail.com",
            "Patryk",
            "Hajduk",
            "patryk_hajduk"
        )
        val order = OrderDto(
            11,
            1,
            10.11.toBigDecimal(),
            listOf(LineItemDto(111, "Product 1", 1), LineItemDto(222, "Product 2", 2))
        )
        val emailData = PrepareOrderEmailRenderData(customer, order)

        //and
        val expectedText = """Witaj Patryk!
Twoje zamówienie nr. 11
o wartości 10,11 zł
jest właśnie pakowane i już za chwilę zostanie przekazane kurierowi.
Pozycje zamówienia: 
  - "Product 1" - 1 szt.
  - "Product 2" - 2 szt.
Pozdrawiamy!"""

        //expect
        Assert.assertEquals(templateEngine.renderPrepareOrderEmail(emailData), expectedText)
    }

}