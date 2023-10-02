package com.example.myedgelighting.utils

import android.content.Context
import android.content.SharedPreferences

class MySharedPrefs(context: Context) {
    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
    private var myEdit: SharedPreferences.Editor = sharedPreferences.edit()

    fun setStringPref(ref: String, value: String) {
        myEdit.putString(ref, value)
        myEdit.apply()
    }


    fun setLongPref(ref: String, value: Long) {
        myEdit.putLong(ref, value)
        myEdit.apply()
    }

    fun setIntPref(ref: String, value: Int) {
        myEdit.putInt(ref, value)
        myEdit.apply()
    }

    fun setFloatPref(ref: String, value: Float) {
        myEdit.putFloat(ref, value)
        myEdit.apply()
    }


    fun getIntPref(ref: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(ref, defaultValue)
    }

    fun getFloatPref(ref: String, defaultValue: Float): Float {
        return sharedPreferences.getFloat(ref, defaultValue)
    }


    fun setBoolPref(ref: String, value: Boolean) {
        myEdit.putBoolean(ref, value)
        myEdit.apply()
    }

    fun getStringPref(ref: String): String? {
        return sharedPreferences.getString(ref, "")
    }

    fun getBoolPref(ref: String): Boolean {
        return sharedPreferences.getBoolean(ref, false)
    }

    fun getIntPref(ref: String): Int {
        return sharedPreferences.getInt(ref, 0)
    }

    fun getLongPref(ref: String): Long {
        return sharedPreferences.getLong(ref, 10000)
    }
}
















