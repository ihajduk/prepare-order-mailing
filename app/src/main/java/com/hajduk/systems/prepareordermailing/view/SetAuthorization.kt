package com.hajduk.systems.prepareordermailing.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import com.hajduk.systems.prepareordermailing.R
import com.hajduk.systems.prepareordermailing.holder.AuthorizationHolder
import kotlinx.android.synthetic.main.activity_set_authorization.*

class SetAuthorization : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_authorization)
        button.setOnClickListener {
            if(isValidInput()) {
                AuthorizationHolder.setCredentialsInContext(applicationContext, clientKeyText.text!!.toString(), clientSecretText.text!!.toString())
                startActivity(Intent(this, ScanBarCode::class.java))
            } else Toast.makeText(this, "Wprowadź klucz i hasło autoryzacji", LENGTH_LONG)
        }
    }

    private fun isValidInput(): Boolean = clientKeyText.text != null && clientSecretText.text != null
}
