package com.hajduk.systems.prepareordermailing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_enter_code.*

class EnterCode : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_code)

        arrowButton.setOnClickListener { view ->
            val activityIntent = Intent(this, Http::class.java)
            activityIntent.putExtra(ORDER_ID, orderInputText.text.toString())
            startActivity(activityIntent)
        }
    }
}
