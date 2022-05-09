package com.a.luxurycar.common.utils

import android.content.Context
import android.preference.PreferenceManager


/**
 * Created by Sandeep on 29-12-2021.
 */

object PrefUtil {


    fun getInt(context: Context?, key: String, defaultValue: Int): Int {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getInt(key, defaultValue)
    }

    fun getLong(context: Context?, key: String, defaultValue: Long): Long {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getLong(key, defaultValue)
    }

    fun putInt(context: Context?, key: String, value: Int) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putInt(key, value).apply()
    }

    fun putLong(context: Context?, key: String, value: Long) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putLong(key, value).apply()
    }

    fun getString(context: Context?, key: String, defaultValue: String): String? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(key, defaultValue)
    }

    fun putString(context: Context?, key: String, value: String) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putString(key, value).apply()
    }

    fun getBoolean(context: Context?, key: String, defaultValue: Boolean): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getBoolean(key, defaultValue)
    }

    fun putBoolean(context: Context?, key: String, value: Boolean) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putBoolean(key, value).apply()
    }

    fun remove(context: Context?, key: String) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().remove(key).apply()
    }

    fun clear(context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().clear().apply()
    }

    fun logoutUser(context: Context) {
        /*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        remove(context, ConstIntent.KEY_IS_LOGGED_IN);
        prefs.edit().commit();*/
    }
}