package com.hajduk.systems.prepareordermailing.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hajduk.systems.prepareordermailing.ORDER_ID
import com.hajduk.systems.prepareordermailing.R
import kotlinx.android.synthetic.main.activity_enter_code.*

class EnterCode : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_code)

        arrowButton.setOnClickListener { _ ->
            val activityIntent = Intent(this, Http::class.java)
            activityIntent.putExtra(ORDER_ID, orderInputText.text.toString())
            startActivity(activityIntent)
        }
    }
}
