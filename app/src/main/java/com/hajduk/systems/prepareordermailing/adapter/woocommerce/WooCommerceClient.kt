package com.hajduk.systems.prepareordermailing.adapter.woocommerce

import android.util.Log
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.result.Result
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.ClientResultStatus.*
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.model.OrderDto
import com.hajduk.systems.prepareordermailing.domain.Order
import com.icoderman.woocommerce.HttpMethod
import com.icoderman.woocommerce.oauth.OAuthConfig
import com.icoderman.woocommerce.oauth.OAuthSignature

class WooCommerceClient(
    private val serverUrl: String,
    private val clientKey: String,
    private val clientSecret: String
) {
    companion object {
        private const val BASE_URL = "/wp-json/wc/v3"
        private const val ORDERS_RESOURCE = "/orders"
        private const val LOGGING_TAG = "woocommerce"
    }

    fun getOrderFuel(orderId: String, onFailure: (message: String) -> Unit, onSuccess: (order: Order) -> Unit) {
        val config = OAuthConfig(serverUrl, clientKey, clientSecret)
        val url = "${config.url}$BASE_URL$ORDERS_RESOURCE/$orderId"
        val finalUrl = "$url?${OAuthSignature.getAsQueryString(config, url, HttpMethod.GET)}"
        Log.d(LOGGING_TAG, finalUrl)
        Fuel.get(finalUrl)
            .responseObject(GsonDeserializer(OrderDto::class.java)) { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        Log.e(LOGGING_TAG, "Exception calling woocommerce: ", result.error)
                        when {
                            response.isNotFound -> onFailure.invoke("Nie znaleziono zamówienia o id $orderId")
                            response.isUnauthorized -> onFailure.invoke("Kod uwierzytalniajacy clientKey: $clientKey jest nieprawidłowy")
                            else -> onFailure.invoke("Wystąpił nieznany błąd. Skontaktuj się z administratorem: hajduk.nieruchomosci@gmail.com")
                        }
                    }
                    is Result.Success -> {
                        onSuccess.invoke(result.get().toDomain())
                    }
                }
            }.join()
    }

    private fun buildUrl(resource: String): String {
        val url = "$serverUrl$BASE_URL$resource"
        return "$url?${OAuthSignature.getAsQueryString(OAuthConfig(serverUrl, clientKey, clientSecret), url, HttpMethod.GET)}"
    }

    private fun executeCall(callRequest: Request): ClientResult {
        val (_, response, result) = callRequest.responseObject(GsonDeserializer(OrderDto::class.java))
        return when {
            response.isSuccessful -> SuccessfulClientResult(result.get())
            response.isNotFound -> FailureClientResult(NOT_FOUND)
            response.isUnauthorized -> FailureClientResult(UNAUTHORIZED, "clientKey $clientKey unauthorized")
            else -> FailureClientResult(OTHER, "Failure occurred. Details: ${result.component2()?.errorData.toString()}")
        }
    }

    class GsonDeserializer<T : Any>(private val clazz: Class<T>) : ResponseDeserializable<T> {

        private companion object {
            private val objectMapper = ObjectMapper().apply {
                propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
                setSerializationInclusion(JsonInclude.Include.NON_NULL)
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                registerModule(JavaTimeModule())
                registerModule(Jdk8Module())
            }
        }

        override fun deserialize(content: String): T = objectMapper.readValue(content, clazz)
    }
}

private fun OrderDto.toDomain(): Order = Order(this.id, this.total, this.customerId, this.lineItems.map { it.name })

val Response.isNotFound
    get() = statusCode == 404

val Response.isUnauthorized
    get() = statusCode == 401

sealed class ClientResult(val status: ClientResultStatus)

data class SuccessfulClientResult(val orderDto: OrderDto) : ClientResult(SUCCESS)

data class FailureClientResult(
    val errorProcessStatus: ClientResultStatus,
    val reason: String = ""
) : ClientResult(errorProcessStatus)

enum class ClientResultStatus {
    SUCCESS, NOT_FOUND, UNAUTHORIZED, OTHER
}