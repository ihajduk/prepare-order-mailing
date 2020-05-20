package com.hajduk.systems.prepareordermailing.service.template

import com.icoderman.woocommerce.HttpMethod
import com.icoderman.woocommerce.oauth.OAuthConfig
import com.icoderman.woocommerce.oauth.OAuthSignature
import org.junit.Ignore
import org.junit.Test
import java.net.URLEncoder
import java.nio.charset.StandardCharsets.UTF_8
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class SignatureTest {

    @Ignore
    @Test
    fun should_generate_signature() {
        val urlBase = "http://localhost/wp-json/wc/v3/orders/1"
        val encodedUrl = URLEncoder.encode(urlBase, UTF_8.name())
        println("EncodedUrl: $encodedUrl")

        val secret = "cs_4794c99927d3b3d5de06981336ce5e199b244e93" + "&"
        val params = mapOf(
            "oauth_consumer_key" to "ck_5f6bc9a58f5f965a99ebac293442c4810051a174",
            "oauth_signature_method" to "HMAC-SHA1", //HmacSHA1 //HMAC-SHA1
            "oauth_timestamp" to System.currentTimeMillis().toString(),
            "oauth_nonce" to UUID.randomUUID().toString()
        )
        println("Params: $params")

        val encodedParameters = TreeMap(params).entries.joinToString("%26") { e -> URLEncoder.encode(e.key, UTF_8.name()) + "%3D" + URLEncoder.encode(e.value, UTF_8.name()) }
        println("encodedParams: $encodedParameters")

        val signatureBaseString = "GET&$encodedUrl&$encodedParameters"
        println(signatureBaseString)
        val instance = Mac.getInstance("HmacSHA1")
        val secretKey = SecretKeySpec(secret.toByteArray(UTF_8), "HmacSHA1")
        instance.init(secretKey)
        //println(String(instance.doFinal(signatureBaseString.toByteArray(StandardCharsets.UTF_8)), StandardCharsets.UTF_8))
        val signature = Base64.getEncoder().encodeToString(instance.doFinal(signatureBaseString.toByteArray(UTF_8)))
        println(signature)
        println("$urlBase?${params.entries.joinToString("&") { e -> "${e.key}=${e.value}" }}&oauth_signature=$signature")
    }

    @Test
    fun java_client_test() {
        val config = OAuthConfig("http://localhost", "ck_5f6bc9a58f5f965a99ebac293442c4810051a174", "cs_4794c99927d3b3d5de06981336ce5e199b244e93")
        val url = "${config.url}/wp-json/wc/v3/orders"
        println("$url?${OAuthSignature.getAsQueryString(config, url, HttpMethod.GET)}")
    }
}