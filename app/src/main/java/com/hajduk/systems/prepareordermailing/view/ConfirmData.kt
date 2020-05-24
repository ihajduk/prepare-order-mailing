package com.hajduk.systems.prepareordermailing.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import com.hajduk.systems.prepareordermailing.R
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.WooCommerceClient
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.model.CustomerDto
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.model.OrderDto
import com.hajduk.systems.prepareordermailing.holder.AuthorizationHolder
import com.hajduk.systems.prepareordermailing.holder.DomainDataHolder
import kotlinx.android.synthetic.main.activity_confirm_data.*

class ConfirmData : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_data)
        retrieveAndDisplayData()

        findViewById<Button>(R.id.dataRejectButton).setOnClickListener { startActivity(Intent(this, ScanBarCode::class.java)) }
        findViewById<Button>(R.id.dataConfirmButton).setOnClickListener { startActivity(Intent(this, TakePhoto::class.java)) }
    }

    private fun retrieveAndDisplayData() {
        val wooCommerceClient =
            WooCommerceClient("http://localhost", "http://10.0.2.2", AuthorizationHolder.clientKey!!, AuthorizationHolder.clientSecret!!)
        retrieveOrderData(wooCommerceClient, DomainDataHolder.demandOrderId())
    }

    private fun retrieveOrderData(wooCommerceClient: WooCommerceClient, orderId: Int) {
        wooCommerceClient.getOrder(
            orderId = orderId,
            onSuccess = { order -> retrieveCustomerData(wooCommerceClient, order) },
            onNotFound = { redirectBackToScanBarCode("Nie znaleziono zamówienia: $orderId") },
            onFailure = { message -> redirectBackToScanBarCode("Wystąpił błąd: $message") }
        )
    }

    private fun retrieveCustomerData(wooCommerceClient: WooCommerceClient, order: OrderDto) {
        wooCommerceClient.getCustomer(
            customerId = order.customerId,
            onSuccess = { customer -> storeAndDisplayData(customer, order) },
            onNotFound = { redirectBackToScanBarCode("Nie znaleziono klienta: ${order.customerId} dla zamówienia: ${order.id}") },
            onFailure = { message -> redirectBackToScanBarCode("Wystąpił błąd: $message") }
        )
    }

    private fun storeAndDisplayData(customer: CustomerDto, order: OrderDto) {
        DomainDataHolder.setCustomer(customer)
        DomainDataHolder.setOrder(order)

        confirmDataTextView.text = """Zamówienie: ${order.id}
Klient: ${customer.firstName} ${customer.lastName}
Pozycje: 
${order.lineItems.map { lineItem -> " - \"${lineItem.name}\" - ${lineItem.quantity} szt." }.joinToString("\n")}"""
    }

    private fun redirectBackToScanBarCode(toastMessage: String) {
        Toast.makeText(this, toastMessage, LENGTH_LONG).show()
        startActivity(Intent(this, ScanBarCode::class.java))
    }
}