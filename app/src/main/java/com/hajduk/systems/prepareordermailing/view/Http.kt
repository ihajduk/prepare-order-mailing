package com.hajduk.systems.prepareordermailing.view

import android.os.Bundle
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

        val wooCommerceClient = WooCommerceClient("http://10.0.2.2:8080", "ck_ac7d7c71127f8399ede3dbbf433744ef3266bb52", "cs_c0c0fd9fe19a81647add48ebee9c1522a361461e")

        wooCommerceClient.getOrderFuel(
            orderId = orderId,
            onFailure = { message ->
                Toast.makeText(this, message, LENGTH_LONG).show()
            },
            onSuccess = { order ->
                httpTextView.setText("Witaj ${order.customerId}, twoje rzeczy: ${order.items} za ${order.total} zara do ciebie lecą byczku") //todo: wrzucanie do maila
            }
        )
    }
}
