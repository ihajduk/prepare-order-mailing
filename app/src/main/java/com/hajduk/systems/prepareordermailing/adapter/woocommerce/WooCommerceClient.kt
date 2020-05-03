package com.hajduk.systems.prepareordermailing.adapter.woocommerce

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.ClientResultStatus.*
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.model.OrderDto

class WooCommerceClient(
    private val serverUrl: String,
    private val clientKey: String,
    private val clientSecret: String
) {
    companion object {
        private const val O_AUTH_CONSUMER_KEY_PARAM_NAME = "oauth_consumer_key"
        private const val O_AUTH_CONSUMER_TOKEN_PARAM_NAME = "oauth_consumer_token"
        private const val O_AUTH_SIGNATURE_METHOD_PARAM_NAME = "oauth_signature_method"
        private const val O_AUTH_SIGNATURE_METHOD = "HMAC-SHA1"
        private const val O_AUTH_VERSION_PARAM_NAME = "oauth_version"
        private const val O_AUTH_VERSION = "1.0"

        private const val BASE_URL = "/wp-json/wc/v3"
        private const val ORDERS_RESOURCE = "/orders"
    }

    fun getOrder(orderId: String): ClientResult {
        return executeCall(buildUrl("$ORDERS_RESOURCE/$orderId").httpGet())
    }

    private fun buildUrl(resource: String): String {
        return "$serverUrl$BASE_URL$resource" +
                "?$O_AUTH_CONSUMER_KEY_PARAM_NAME=$clientKey" +
                "&$O_AUTH_CONSUMER_TOKEN_PARAM_NAME=$clientSecret" +
                "&$O_AUTH_SIGNATURE_METHOD_PARAM_NAME=$O_AUTH_SIGNATURE_METHOD" +
                "&$O_AUTH_VERSION_PARAM_NAME=$O_AUTH_VERSION"
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
            private val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        }

        override fun deserialize(content: String): T = gson.fromJson(content, clazz)
    }
}

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