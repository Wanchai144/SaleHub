package com.example.samplemvp.dialog

import android.content.Context
import android.content.SharedPreferences

class Preferences constructor(private var context: Context){

    companion object {
        private const val FILENAME = "wewillapp_project"
        private const val ID = "id"
        private const val IMAGE = "image"
        private const val TOKEN = "token"
        private const val PUSH_TOKEN = "push_token"
        private const val LANGUAGE = "language"
        private const val NOTIFICATION = "notification"
    }


    fun getUserId(): String? {
        return getString(ID)
    }

    fun saveUserId(account: String) {
        saveString(ID, account)
    }

    fun saveUserImage(image: String) {
        saveString(IMAGE, image)
    }

    fun getUserImage(): String? {
        return getString(IMAGE)
    }

    fun getToken(): String {
        return getString(TOKEN) ?: ""
    }

    fun saveToken(token: String) {
        saveString(TOKEN, "Bearer $token")
    }

    fun getPushToken(): String? {
        return getString(PUSH_TOKEN)
    }

    fun savePushToken(push_token: String) {
        saveString(PUSH_TOKEN, push_token)
    }

    fun getLanguage(): String? {
        return getString(LANGUAGE)
    }

    fun saveLanguage(language: String) {
        saveString(LANGUAGE, language)
    }

    fun getNotification(): Boolean? {
        return getBoolean(NOTIFICATION)
    }

    fun saveNotification(language: Boolean) {
        saveBoolean(NOTIFICATION, language)
    }

    fun getLanguageHeader(): String {
        return if (getLanguage() == "en")
            "en_US"
        else
            "th_TH"
    }

    fun clearDataLogout(){
        saveString(TOKEN, "")
    }
    internal fun clear() {
        getSharedPreferences().edit().clear().apply()
    }

    private fun saveString(key: String, value: String) {
        val editor = getSharedPreferences().edit()
        editor.putString(key, value)
        editor.apply()
    }


    private fun getString(key: String): String? {
        return getSharedPreferences().getString(key, null)
    }

    private fun saveBoolean(key: String, value: Boolean) {
        val editor = getSharedPreferences().edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun getBoolean(key: String): Boolean {
        return getSharedPreferences().getBoolean(key, false)
    }

    private fun getSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
    }

}