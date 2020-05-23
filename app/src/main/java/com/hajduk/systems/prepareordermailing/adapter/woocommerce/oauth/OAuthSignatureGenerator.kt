package com.hajduk.systems.prepareordermailing.adapter.woocommerce.oauth

import android.util.Base64
import com.icoderman.woocommerce.HttpMethod
import com.icoderman.woocommerce.oauth.OAuthConfig
import com.icoderman.woocommerce.oauth.OAuthHeader
import com.icoderman.woocommerce.oauth.SpecialSymbol
import java.io.UnsupportedEncodingException
import java.lang.Boolean
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.stream.Collectors
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class OAuthSignatureGenerator {

    companion object {
        private const val UTF_8 = "UTF-8";
        private const val HMAC_SHA256 = "HmacSHA256";
        private const val SIGNATURE_METHOD_HMAC_SHA256 = "HMAC-SHA256";
        private const val BASE_SIGNATURE_FORMAT = "%s&%s&%s";
        private const val DELETE_PARAM_FORCE = "force";
    }

    fun getAsMap(
        config: OAuthConfig?,
        endpoint: String?,
        httpMethod: HttpMethod?,
        params: Map<String, String>?
    ): MutableMap<String, String> {
        if (config == null || endpoint == null || httpMethod == null) {
            return mutableMapOf()
        }
        val authParams: MutableMap<String, String> = HashMap()
        authParams[OAuthHeader.OAUTH_CONSUMER_KEY.value] = config.consumerKey
        authParams[OAuthHeader.OAUTH_TIMESTAMP.value] = (System.currentTimeMillis() / 1000L).toString()
        authParams[OAuthHeader.OAUTH_NONCE.value] = UUID.randomUUID().toString()
        authParams[OAuthHeader.OAUTH_SIGNATURE_METHOD.value] = SIGNATURE_METHOD_HMAC_SHA256
        authParams.putAll(params!!)

        // WooCommerce specified param
        if (HttpMethod.DELETE == httpMethod) {
            authParams[DELETE_PARAM_FORCE] = Boolean.TRUE.toString()
        }
        val oAuthSignature = generateOAuthSignature(config.consumerSecret, endpoint, httpMethod, authParams)
        authParams[OAuthHeader.OAUTH_SIGNATURE.value] = oAuthSignature
        return authParams
    }

    fun getAsQueryString(
        config: OAuthConfig?,
        endpoint: String?,
        httpMethod: HttpMethod?,
        params: Map<String, String>?
    ): String? {
        if (config == null || endpoint == null || httpMethod == null) {
            return ""
        }
        val oauthParameters = getAsMap(config, endpoint, httpMethod, params)
        val encodedSignature = oauthParameters[OAuthHeader.OAUTH_SIGNATURE.value]!!
            .replace(SpecialSymbol.PLUS.plain, SpecialSymbol.PLUS.encoded)
        oauthParameters.put(OAuthHeader.OAUTH_SIGNATURE.value, encodedSignature)
        return mapToString(oauthParameters, SpecialSymbol.EQUAL.plain, SpecialSymbol.AMP.plain)
    }

    fun getAsQueryString(config: OAuthConfig?, endpoint: String?, httpMethod: HttpMethod?): String? {
        return getAsQueryString(config, endpoint, httpMethod, mutableMapOf())
    }

    private fun generateOAuthSignature(
        customerSecret: String,
        endpoint: String,
        httpMethod: HttpMethod,
        parameters: Map<String, String>
    ): String {
        val signatureBaseString = getSignatureBaseString(endpoint, httpMethod.name, parameters)
        // v1, v2
        val secret = customerSecret + SpecialSymbol.AMP.plain
        return signBaseString(secret, signatureBaseString)
    }

    private fun signBaseString(secret: String, signatureBaseString: String): String {
        val macInstance: Mac
        return try {
            macInstance = Mac.getInstance(HMAC_SHA256)
            val secretKey = SecretKeySpec(secret.toByteArray(charset(UTF_8)),
                HMAC_SHA256
            )
            macInstance.init(secretKey)
            String(Base64.encode(macInstance.doFinal(signatureBaseString.toByteArray(charset(UTF_8))), 0), StandardCharsets.UTF_8)
                .replace("\n", "")//todo: here
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: InvalidKeyException) {
            throw RuntimeException(e)
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException(e)
        }
    }

    private fun urlEncode(s: String): String {
        return try {
            URLEncoder.encode(s, UTF_8)
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException(e)
        }
    }

    private fun getSignatureBaseString(url: String, method: String, parameters: Map<String, String>): String {
        val requestURL = urlEncode(url)
        // 1. Percent encode every key and value that will be signed.
        var encodedParameters = percentEncodeParameters(parameters)

        // 2. Sort the list of parameters alphabetically by encoded key.
        encodedParameters = getSortedParameters(encodedParameters)
        val paramsString = mapToString(encodedParameters, SpecialSymbol.EQUAL.encoded, SpecialSymbol.AMP.encoded)
        return String.format(BASE_SIGNATURE_FORMAT, method, requestURL, paramsString)
    }

    private fun mapToString(
        paramsMap: Map<String, String>,
        keyValueDelimiter: String,
        paramsDelimiter: String
    ): String? {
        return paramsMap.entries.stream()
            .map { entry: Map.Entry<String, String> -> entry.key + keyValueDelimiter + entry.value }
            .collect(Collectors.joining(paramsDelimiter))
    }

    private fun percentEncodeParameters(parameters: Map<String, String>): Map<String, String> {
        val encodedParamsMap: MutableMap<String, String> = HashMap()
        for ((key, value) in parameters) {
            encodedParamsMap[percentEncode(key)] = percentEncode(value)
        }
        return encodedParamsMap
    }

    private fun percentEncode(s: String): String {
        return try {
            URLEncoder.encode(s, UTF_8) // OAuth encodes some characters differently:
                .replace(SpecialSymbol.PLUS.plain, SpecialSymbol.PLUS.encoded)
                .replace(SpecialSymbol.STAR.plain, SpecialSymbol.STAR.encoded)
                .replace(SpecialSymbol.TILDE.encoded, SpecialSymbol.TILDE.plain)
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException(e.message, e)
        }
    }

    private fun getSortedParameters(parameters: Map<String, String>): Map<String, String> {
        return TreeMap(parameters)
    }
}