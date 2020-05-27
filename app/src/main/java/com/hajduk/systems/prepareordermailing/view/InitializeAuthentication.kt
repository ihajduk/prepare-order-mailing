package com.hajduk.systems.prepareordermailing.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hajduk.systems.prepareordermailing.R
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.WooCommerceClient
import com.hajduk.systems.prepareordermailing.holder.AuthorizationHolder
import kotlinx.android.synthetic.main.activity_initialize_authentication.*

class InitializeAuthentication : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initialize_authentication)
        button.setOnClickListener {
            when (isInputValid()) {
                true -> checkAuthenticationValid()
                false -> displayAuthenticationFailedMessage()
            }
        }
    }

    private fun isInputValid(): Boolean = consumerKeyText.text?.isNotBlank() ?: false && consumerSecretText.text?.isNotBlank() ?: false

    private fun checkAuthenticationValid() {
        WooCommerceClient("http://localhost", "http://10.0.2.2", consumerKeyText.text!!.toString().trim(), consumerSecretText.text!!.toString().trim())
            .checkAuthentication(
                onSuccess = {
                    AuthorizationHolder.setCredentialsInContext(applicationContext, consumerKeyText.text!!.toString().trim(), consumerSecretText.text!!.toString().trim())
                    startActivity(Intent(this, ScanBarCode::class.java))
                },
                onFailure = { displayAuthenticationFailedMessage() }
            )
    }

    private fun displayAuthenticationFailedMessage() {
        Toast.makeText(this, "Błąd autentykacji, sprawdź dane", Toast.LENGTH_LONG).show()
    }
}