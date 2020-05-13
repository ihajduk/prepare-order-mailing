package com.hajduk.systems.prepareordermailing.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.hajduk.systems.prepareordermailing.ORDER_ID
import com.hajduk.systems.prepareordermailing.R
import kotlinx.android.synthetic.main.activity_http.*

class Http : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http)

        val orderId = intent.getStringExtra(ORDER_ID) ?: ""

        val httpAsync = "http://10.0.2.2:8080/data/order/$orderId"
            .httpGet()
            .responseObject(Order.Deserializer()) { request, response, result ->
                val (_, _) = result
                val data = result.get()

                httpTextView.text = "Witaj ${data.name}, przesyłka z: ${data.offer} oczekuje na wysyłkę"
            }

        httpAsync.join()
    }

    data class Order(val name: String, val offer: String) {
        class Deserializer : ResponseDeserializable<Order> {
            override fun deserialize(content: String): Order = Gson().fromJson(content, Order::class.java)
        }
    }

}
