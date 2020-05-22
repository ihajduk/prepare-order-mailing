package com.hajduk.systems.prepareordermailing.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.hajduk.systems.prepareordermailing.R
import kotlinx.android.synthetic.main.activity_scan.*

class KScanBarCode : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_scan)
        lifecycle.addObserver(view_barcode)
        view_barcode.barcodes.observe(this, Observer { barcode ->
            println("Barcode: $barcode")
        })
    }
}