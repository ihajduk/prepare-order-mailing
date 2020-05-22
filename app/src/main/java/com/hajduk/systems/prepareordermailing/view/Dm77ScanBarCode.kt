package com.hajduk.systems.prepareordermailing.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView


class Dm77ScanBarCode : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var mScannerView: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        //mScannerView = ZXingScannerView(this)
        setContentView(mScannerView)
    }

    override fun onResume() {
        super.onResume()
        mScannerView = ZXingScannerView(this)
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    override fun handleResult(rawResult: Result?) {
        println("Bar code: ${rawResult?.text}")

    }

//    override fun onPause() {
//        super.onPause()
//        mScannerView.stopCamera()
//    }

//    override fun onResume() {
//        super.onResume()
//        mScannerView.setResultHandler(this)
//        mScannerView.startCamera()
//    }

}