package com.hajduk.systems.prepareordermailing.adapter.woocommerce

import android.util.Log
import com.github.kittinunf.fuel.Fuel.get
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.result.Result.Failure
import com.github.kittinunf.result.Result.Success
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.model.OrderDto
import com.hajduk.systems.prepareordermailing.domain.Order
import org.apache.commons.codec.binary.Hex
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


class WooCommerceClient(
    private val serverUrl: String,
    private val clientKey: String,
    private val clientSecret: String
) {
    companion object {
        private const val O_AUTH_CONSUMER_KEY_PARAM_NAME = "oauth_consumer_key"
        private const val O_AUTH_SIGNATURE_PARAM_NAME = "oauth_consumer_key"
        private const val O_AUTH_CONSUMER_TOKEN_PARAM_NAME = "oauth_consumer_token"
        private const val O_AUTH_SIGNATURE_METHOD_PARAM_NAME = "oauth_signature_method"
        private const val O_AUTH_SIGNATURE_METHOD = "HMAC-SHA1"
        private const val O_AUTH_VERSION_PARAM_NAME = "oauth_version"
        private const val O_AUTH_VERSION = "1.0"

        private const val BASE_URL = "/wp-json/wc/v3"
        private const val ORDERS_RESOURCE = "/orders"

        private const val LOGGING_TAG = "WOOCOMMERCE_CLIENT"
    }

    fun getOrder(orderId: String, onFailure: (message: String) -> Unit, onSuccess: (order: Order) -> Unit) {
        get(buildUrl("$ORDERS_RESOURCE/$orderId"))
            .responseObject(GsonDeserializer(OrderDto::class.java)) { request, response, result ->
            when(result) {
                is Failure -> {
                    Log.e(LOGGING_TAG, "Exception calling woocommerce: ", result.error)
                    when {
                        response.isNotFound -> onFailure.invoke("Nie znaleziono zamówienia o id $orderId")
                        response.isUnauthorized -> onFailure.invoke("Kod uwierzytalniajacy clientKey: $clientKey jest nieprawidłowy")
                        else -> onFailure.invoke("Wystąpił nieznany błąd. Skontaktuj się z administratorem: hajduk.nieruchomosci@gmail.com")
                    }
                }
                is Success -> {
                    onSuccess.invoke(result.get().toDomain())
                }
            }
        }.join()
    }

    fun encode(key: String, data: String): String? {
        val sha256_HMAC = Mac.getInstance("HmacSHA256")
        val secret_key = SecretKeySpec(key.toByteArray(charset("UTF-8")), "HmacSHA256")
        sha256_HMAC.init(secret_key)
        return Hex.encodeHexString(sha256_HMAC.doFinal(data.toByteArray(charset("UTF-8"))))
    }

    private fun buildUrl(resource: String): String {
        val base = "$serverUrl$BASE_URL$resource" +
                "?$O_AUTH_CONSUMER_KEY_PARAM_NAME=$clientKey" +
                "&$O_AUTH_CONSUMER_TOKEN_PARAM_NAME=$clientSecret" +
                "&$O_AUTH_SIGNATURE_METHOD_PARAM_NAME=$O_AUTH_SIGNATURE_METHOD" +
                "&$O_AUTH_VERSION_PARAM_NAME=$O_AUTH_VERSION" +
                "&oauth_timestamp=null" +
                "&oauth_nonce=null"

        val encode = encode("abcd&1234", base)

        return base+encode
    }

    class GsonDeserializer<T : Any>(private val clazz: Class<T>) : ResponseDeserializable<T> {

        private companion object {
            private val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        }

        override fun deserialize(content: String): T = gson.fromJson(content, clazz)
    }
}

private fun OrderDto.toDomain(): Order = Order(this.id, this.total, this.customerId, this.lineItems.map { it.name })

val Response.isNotFound
    get() = statusCode == 404

val Response.isUnauthorized
    get() = statusCode == 401
