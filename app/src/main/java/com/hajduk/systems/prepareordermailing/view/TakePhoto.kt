package com.hajduk.systems.prepareordermailing.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.hajduk.systems.prepareordermailing.R
import com.hajduk.systems.prepareordermailing.holder.ApplicationDataHolder
import kotlinx.android.synthetic.main.activity_take_photo.*
import java.io.File


class TakePhoto : AppCompatActivity() {

    companion object {
        private const val PHOTO_CAPTURE_REQUEST_CODE = 1
    }

    private lateinit var photoFileName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_photo)
        takePhotoButton.setOnClickListener { initializeImageCaptureIntent() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val photoFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), photoFileName)
        if (PHOTO_CAPTURE_REQUEST_CODE == requestCode && Activity.RESULT_OK == resultCode) {
            ApplicationDataHolder.instance.setPhotoFileAbsolutePath(photoFile.absolutePath)
            takePhotoTextView.text = ""
            startActivity(Intent(this, SendEmail::class.java))
        } else {
            if (photoFile.exists()) {
                photoFile.delete()
            }
            takePhotoTextView.text = "Zdjęcie nie zaakceptowane, wykonaj zdjęcie ponownie"
        }
    }

    private fun initializeImageCaptureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePhotoIntent ->
            takePhotoIntent.resolveActivity(packageManager)?.also {
                val photoFile = createPhotoFile()
                photoFileName = photoFile.name
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, "com.hajduk.systems.prepareordermailing.fileprovider", photoFile))
                startActivityForResult(takePhotoIntent, PHOTO_CAPTURE_REQUEST_CODE)
            }
        }
    }

    private fun createPhotoFile(): File {
        return File.createTempFile("Order_${ApplicationDataHolder.instance.demandOrderId()}_", ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES))
    }
}