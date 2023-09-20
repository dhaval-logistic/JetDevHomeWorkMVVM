package com.example.jetdevhomeworkmvvm.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun saveUserData(userName: String?, psw: String?, token: String?) {
        editor.putString(KEY_USERNAME, userName)
        editor.putString(KEY_USERID, psw)
        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }


    val userName: String?
        get() = sharedPreferences.getString(KEY_USERNAME, "")
    val passwordData: String?
        get() = sharedPreferences.getString(KEY_USERID, "")
    val tokenData: String?
        get() = sharedPreferences.getString(KEY_TOKEN, "")


    fun clearUserData() {
        editor.remove(KEY_USERNAME)
        editor.remove(KEY_USERID)
        editor.remove(KEY_TOKEN)
        editor.apply()
    }

    companion object {
        private const val PREF_NAME = "UserSession"
        private const val KEY_USERNAME = "username"
        private const val KEY_USERID = "password"
        private const val KEY_TOKEN = "token"
    }
}