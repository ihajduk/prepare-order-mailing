package com.hajduk.systems.prepareordermailing.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hajduk.systems.prepareordermailing.R
import com.hajduk.systems.prepareordermailing.holder.ApplicationDataHolder
import kotlinx.android.synthetic.main.activity_index.*

class Index : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index)
        ApplicationDataHolder.instance.clearData()
        scanButton.setOnClickListener { startActivity(Intent(this, ScanBarCode::class.java)) }
    }
}
