package com.hajduk.systems.prepareordermailing.holder

import android.content.Context

object AuthorizationHolder {
    const val APP_PREFS = "APP_PREFS"
    const val CLIENT_KEY = "CLIENT_KEY"
    const val CLIENT_SECRET = "CLIENT_SECRET"

    var clientKey: String? = null
        private set
    var clientSecret: String? = null
        private set

    fun areCredentialsInContext(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(APP_PREFS, 0)
        val credentialsPresent = sharedPreferences.contains(CLIENT_KEY) && sharedPreferences.contains(CLIENT_SECRET)
        if (credentialsPresent) {
            clientKey = sharedPreferences.getString(CLIENT_KEY, null)
            clientSecret = sharedPreferences.getString(CLIENT_SECRET, null)
        }
        return credentialsPresent //if credentialsPresent == true return true else return false
    }

    fun setCredentialsInContext(context: Context, clientKey: String, clientSecret: String) {
        val editor = context.getSharedPreferences(APP_PREFS, 0).edit()
        editor.putString(CLIENT_KEY, clientKey)
        editor.putString(CLIENT_SECRET, clientSecret)
        editor.apply()
        this.clientKey = clientKey
        this.clientSecret = clientSecret
    }

    fun clearCredentials(context: Context) {
        val sharedPreferences = context.getSharedPreferences(APP_PREFS, 0)
        if (sharedPreferences.contains(CLIENT_KEY) && sharedPreferences.contains(CLIENT_SECRET)) {
            val editor = sharedPreferences.edit()
            editor.remove(CLIENT_KEY)
            editor.remove(CLIENT_SECRET)
            editor.apply()
        }
    }
}