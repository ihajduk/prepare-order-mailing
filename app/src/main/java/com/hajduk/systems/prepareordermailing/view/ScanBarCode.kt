package com.hajduk.systems.prepareordermailing.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hajduk.systems.prepareordermailing.R
import com.hajduk.systems.prepareordermailing.holder.AuthorizationHolder
import com.hajduk.systems.prepareordermailing.holder.DomainDataHolder
import kotlinx.android.synthetic.main.activity_scan_bar_code.*


class ScanBarCode : AppCompatActivity() {

    companion object {
        private const val BAR_CODE_SCAN_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DomainDataHolder.clearData()
        if (AuthorizationHolder.areCredentialsInContext(this)) {
            setContentView(R.layout.activity_scan_bar_code)
            scanBarCodeButton.setOnClickListener { initializeBarCodeScanning() }
        } else {
            startActivity(Intent(this, InitializeAuthentication::class.java))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (BAR_CODE_SCAN_REQUEST == requestCode && Activity.RESULT_OK == resultCode) {
            DomainDataHolder.setOrderId(data?.getStringExtra("SCAN_RESULT")?.toInt() ?: throw IllegalStateException("Cannot resolve orderId"))
            startActivity(Intent(this, ConfirmData::class.java))
        }
    }

    private fun initializeBarCodeScanning() {
        startActivityForResult(Intent("com.google.zxing.client.android.SCAN").apply {
            putExtra("SCAN_MODE", "BAR_CODE_MODE")
            putExtra("SAVE_HISTORY", false)
        }, BAR_CODE_SCAN_REQUEST)
    }
}