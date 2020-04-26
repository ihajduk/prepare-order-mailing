package com.hajduk.systems.prepareordermailing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_index.*

class Index : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)

        scanButton.setOnClickListener { view ->
            val activityIntent = Intent(this, EnterCode::class.java)
            startActivity(activityIntent)
        }
    }
}
