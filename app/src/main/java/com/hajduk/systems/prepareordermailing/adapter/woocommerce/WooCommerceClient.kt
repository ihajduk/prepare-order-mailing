package com.hajduk.systems.prepareordermailing.adapter.woocommerce

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.result.Result
import com.google.gson.JsonParser
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.WooCommerceClient.MessageUtils.messageTranslator
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.WooCommerceClient.MessageUtils.unknown401Message
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.model.CustomerDto
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.model.OrderDto
import com.hajduk.systems.prepareordermailing.adapter.woocommerce.oauth.OAuthSignatureGenerator
import com.icoderman.woocommerce.HttpMethod
import com.icoderman.woocommerce.oauth.OAuthConfig
import org.slf4j.LoggerFactory

class WooCommerceClient(
    private val serverUrl: String,
    private val proxyUrl: String,
    private val clientKey: String,
    private val clientSecret: String
) {
    companion object {
        private const val BASE_URL = "/wp-json/wc/v3"
        private const val SETTINGS_RESOURCE = "/settings"
        private const val ORDERS_RESOURCE = "/orders"
        private const val CUSTOMERS_RESOURCE = "/customers"

        private val oAuthSignatureGenerator = OAuthSignatureGenerator()
        private val logger = LoggerFactory.getLogger(WooCommerceClient::class.java)
    }

    fun checkAuthentication(onSuccess: () -> Unit, onFailure: () -> Unit) {
        getResource("$BASE_URL$SETTINGS_RESOURCE", List::class.java, { onSuccess() }, { onFailure() }, { onFailure() })
    }

    fun getCustomer(customerId: Int, onSuccess: (order: CustomerDto) -> Unit, onNotFound: () -> Unit, onFailure: (message: String) -> Unit) {
        getResource("$BASE_URL$CUSTOMERS_RESOURCE/$customerId", CustomerDto::class.java, onSuccess, onNotFound, onFailure)
    }

    fun getOrder(orderId: Int, onSuccess: (order: OrderDto) -> Unit, onNotFound: () -> Unit, onFailure: (message: String) -> Unit) {
        getResource("$BASE_URL$ORDERS_RESOURCE/$orderId", OrderDto::class.java, onSuccess, onNotFound, onFailure)
    }

    private fun <T : Any> getResource(resourceUrl: String, clazz: Class<T>, onSuccess: (data: T) -> Unit, onNotFound: () -> Unit, onFailure: (message: String) -> Unit) {
        Fuel.get(createSignedUrl(resourceUrl))
            .responseObject(GsonDeserializer(clazz)) { _, response, result ->
                when (result) {
                    is Result.Failure -> {
                        when {
                            response.isNotFound -> onNotFound.invoke()
                            response.isUnauthorized -> onFailure.invoke(extractFailureMessage(response))
                            else -> {
                                logger.error("Exception when accessing resource: $resourceUrl", result.error)
                                onFailure.invoke("Communication error")
                            }
                        }
                    }
                    is Result.Success -> {
                        onSuccess.invoke(result.get())
                    }
                }
            }.join()
    }

    private fun extractFailureMessage(response: Response): String {
        val parsedUnauthorizedMessage = JsonParser().parse(String(response.data)).asJsonObject.get("message").asString
        return messageTranslator[parsedUnauthorizedMessage] ?: unknown401Message
    }

    private fun createSignedUrl(resourceUrl: String): String {
        return "$proxyUrl$resourceUrl?${oAuthSignatureGenerator.getAsQueryString(OAuthConfig(serverUrl, clientKey, clientSecret), "$serverUrl$resourceUrl", HttpMethod.GET)}"
    }

    private class GsonDeserializer<T : Any>(private val clazz: Class<T>) : ResponseDeserializable<T> {

        private companion object {
            private val objectMapper = ObjectMapper().apply {
                propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
                setSerializationInclusion(JsonInclude.Include.NON_NULL)
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                registerModule(KotlinModule())
            }
        }

        override fun deserialize(content: String): T = objectMapper.readValue(content, clazz)
    }

    private object MessageUtils {
        val messageTranslator = mapOf(
            "Consumer key is invalid." to "Podano niewłaściwy klucz uwierzytelniający",
            "Invalid signature - provided signature does not match." to "Niepowodzenie uwierzytelniania do sklepu - sprawdz poprawnosc hasła"
        )
        val unknown401Message = "Blad uwierzytelnienia. Skontaktuj sie z administratorem: hajduk.nieruchomosci@gmail.com"
    }
}

val Response.isNotFound
    get() = statusCode == 404

val Response.isUnauthorized
    get() = statusCode == 401