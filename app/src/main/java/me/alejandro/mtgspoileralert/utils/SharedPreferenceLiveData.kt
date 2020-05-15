package me.alejandro.mtgspoileralert.utils

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

abstract class SharedPreferenceLiveData<T>(
    private val preferences: SharedPreferences,
    private val key: String,
    private val defaultValue: T
) : LiveData<T>() {

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == this.key) {
                value = getValueFromPreferences(key, defaultValue)
            }
        }

    abstract fun getValueFromPreferences(key: String, defaultValue: T): T

    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(key, defaultValue)
        preferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        preferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }
}

class SharedPreferenceIntLiveData(
    private val sharedPreferences: SharedPreferences,
    key: String,
    defaultValue: Int
) : SharedPreferenceLiveData<Int>(sharedPreferences, key, defaultValue) {
    override fun getValueFromPreferences(key: String, defaultValue: Int): Int =
        sharedPreferences.getInt(key, defaultValue)
}

class SharedPreferenceStringLiveData(
    private val sharedPreferences: SharedPreferences,
    key: String,
    defaultValue: String
) : SharedPreferenceLiveData<String>(sharedPreferences, key, defaultValue) {
    override fun getValueFromPreferences(key: String, defaultValue: String): String =
        sharedPreferences.getString(key, defaultValue)!!
}

class SharedPreferenceBooleanLiveData(
    private val sharedPreferences: SharedPreferences,
    key: String,
    defaultValue: Boolean
) : SharedPreferenceLiveData<Boolean>(sharedPreferences, key, defaultValue) {
    override fun getValueFromPreferences(key: String, defaultValue: Boolean): Boolean =
        sharedPreferences.getBoolean(key, defaultValue)
}

class SharedPreferenceFloatLiveData(
    private val sharedPreferences: SharedPreferences,
    key: String,
    defaultValue: Float
) : SharedPreferenceLiveData<Float>(sharedPreferences, key, defaultValue) {
    override fun getValueFromPreferences(key: String, defaultValue: Float): Float =
        sharedPreferences.getFloat(key, defaultValue)
}

class SharedPreferenceLongLiveData(
    private val sharedPreferences: SharedPreferences,
    key: String,
    defaultValue: Long
) : SharedPreferenceLiveData<Long>(sharedPreferences, key, defaultValue) {
    override fun getValueFromPreferences(key: String, defaultValue: Long): Long =
        sharedPreferences.getLong(key, defaultValue)
}

class SharedPreferenceStringSetLiveData(
    private val sharedPreferences: SharedPreferences,
    key: String,
    defaultValue: Set<String>
) : SharedPreferenceLiveData<Set<String>>(sharedPreferences, key, defaultValue) {
    override fun getValueFromPreferences(key: String, defaultValue: Set<String>): Set<String> =
        sharedPreferences.getStringSet(key, defaultValue)!!
}

fun SharedPreferences.intLiveData(key: String, defaultValue: Int): SharedPreferenceLiveData<Int> =
    SharedPreferenceIntLiveData(this, key, defaultValue)

fun SharedPreferences.stringLiveData(
    key: String,
    defaultValue: String
): SharedPreferenceLiveData<String> =
    SharedPreferenceStringLiveData(this, key, defaultValue)

fun SharedPreferences.booleanLiveData(
    key: String,
    defaultValue: Boolean
): SharedPreferenceLiveData<Boolean> =
    SharedPreferenceBooleanLiveData(this, key, defaultValue)

fun SharedPreferences.floatLiveData(
    key: String,
    defaultValue: Float
): SharedPreferenceLiveData<Float> =
    SharedPreferenceFloatLiveData(this, key, defaultValue)

fun SharedPreferences.longLiveData(
    key: String,
    defaultValue: Long
): SharedPreferenceLiveData<Long> =
    SharedPreferenceLongLiveData(this, key, defaultValue)

fun SharedPreferences.stringSetLiveData(
    key: String,
    defaultValue: Set<String>
): SharedPreferenceLiveData<Set<String>> =
    SharedPreferenceStringSetLiveData(this, key, defaultValue)

