package com.hajduk.systems.prepareordermailing.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hajduk.systems.prepareordermailing.R
import com.hajduk.systems.prepareordermailing.exception.ThreadExceptionHandler
import com.hajduk.systems.prepareordermailing.holder.AuthorizationHolder
import com.hajduk.systems.prepareordermailing.holder.DomainDataHolder
import kotlinx.android.synthetic.main.activity_scan_bar_code.*


class ScanBarCode : AppCompatActivity() {

    companion object {
        private const val BAR_CODE_SCAN_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeApp()
        if (AuthorizationHolder.areCredentialsInContext(this)) {
            setContentView(R.layout.activity_scan_bar_code)
            scanBarCodeButton.setOnClickListener { initializeBarCodeScanning() }
        } else {
            startActivity(Intent(this, InitializeAuthentication::class.java))
        }
    }

    private fun initializeApp() {
        ThreadExceptionHandler.configure()
        DomainDataHolder.clearData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (BAR_CODE_SCAN_REQUEST == requestCode && Activity.RESULT_OK == resultCode) {
            DomainDataHolder.setOrderId(data?.getStringExtra("SCAN_RESULT")?.toInt() ?: throw IllegalStateException("Cannot resolve orderId"))
            startActivity(Intent(this, ConfirmData::class.java))
        }
    }

    private fun initializeBarCodeScanning() {
        val zxingScanBarCodeIntent = Intent("com.google.zxing.client.android.SCAN").apply {
            putExtra("SCAN_MODE", "BAR_CODE_MODE")
            putExtra("SAVE_HISTORY", false)
        }
        return if (zxingScanBarCodeIntent.resolveActivity(packageManager) == null) {
            Toast.makeText(this, "Skanowanie nie może być rozpoczęte, zaintaluj Zxing Bar Code Scanner", Toast.LENGTH_LONG).show()
        } else {
            startActivityForResult(zxingScanBarCodeIntent, BAR_CODE_SCAN_REQUEST)
        }
    }
}