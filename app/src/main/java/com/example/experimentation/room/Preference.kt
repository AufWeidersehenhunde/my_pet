package com.example.experimentation.room

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Preference @Inject constructor(@ApplicationContext context: Context) {

    private val PREFS_NAME = "pref"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, value)
        editor.commit()
    }

    fun getValue(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, "")
    }

    fun remove(KEY_NAME: String):String?{
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.commit()
        return null
    }
}