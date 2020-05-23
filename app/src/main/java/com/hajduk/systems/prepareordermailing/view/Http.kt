package com.hajduk.systems.prepareordermailing.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import com.hajduk.systems.prepareordermailing.ORDER_ID
import com.hajduk.systems.prepareordermailing.R
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.WooCommerceClient
import kotlinx.android.synthetic.main.activity_http.*

class Http : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http)

        val orderId = intent.getStringExtra(ORDER_ID) ?: ""

        val wooCommerceClient =
            WooCommerceClient("http://localhost", "http://10.0.2.2", "ck_132211fbf3952da2e259ac134d456f59c15ae8d3", "cs_b850bfa01ca6eef3c4ae1fb73b9d781524651cc4")

        wooCommerceClient.getOrder(
            orderId = orderId,
            onSuccess = { order ->
                Log.d("order", "order = $order")
                httpTextView.setText("Witaj ${order.customerId}, twoje rzeczy: ${order.lineItems} za ${order.total} zara do ciebie lecą byczku")
            },
            onNotFound = { Toast.makeText(this, "Nie znaleziono zamówienia: $orderId", LENGTH_LONG).show() },
            onFailure = { message ->
                Toast.makeText(this, message, LENGTH_LONG).show()
            }
        )
    }
}
