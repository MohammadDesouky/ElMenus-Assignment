package com.elmenus.assignment.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {

    private const val SHARED_PREFERENCE_NAME = "SHARED_PREFS_FILE_NAME"

    fun getInt(context: Context, intKey: String, defaultValue: Int): Int {
        return getSharedPreference(context)?.getInt(intKey, defaultValue) ?: defaultValue
    }

    fun setInt(context: Context, intKey: String, intValue: Int) {
        getSharedPreference(context)?.edit()?.putInt(intKey, intValue)?.apply()
    }

    private fun getSharedPreference(context: Context): SharedPreferences? {
        return context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }
}