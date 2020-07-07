package com.shadowings.apodkmp.mock

import com.russhwolf.settings.Settings

class VolatileSettings : Settings {

    override val keys: Set<String>
        get() = setOf()

    override val size: Int
        get() = 0

    val hashmap: HashMap<String, Any> = HashMap()

    override fun clear() {
    }

    fun <T> get(key: String, defaultValue: T): T {
        return if (hashmap.containsKey(key)) {
            try {
                hashmap[key] as T
            } catch (e: Exception) {
                defaultValue
            }
        } else {
            defaultValue
        }
    }

    fun <T> getOrNull(key: String): T? {
        return if (hashmap.containsKey(key)) {
            try {
                hashmap[key] as T
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return get(key, defaultValue)
    }

    override fun getBooleanOrNull(key: String): Boolean? {
        return getOrNull(key)
    }

    override fun getDouble(key: String, defaultValue: Double): Double {
        return get(key, defaultValue)
    }

    override fun getDoubleOrNull(key: String): Double? {
        return getOrNull(key)
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return get(key, defaultValue)
    }

    override fun getFloatOrNull(key: String): Float? {
        return getOrNull(key)
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return get(key, defaultValue)
    }

    override fun getIntOrNull(key: String): Int? {
        return getOrNull(key)
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return get(key, defaultValue)
    }

    override fun getLongOrNull(key: String): Long? {
        return getOrNull(key)
    }

    override fun getString(key: String, defaultValue: String): String {
        return get(key, defaultValue)
    }

    override fun getStringOrNull(key: String): String? {
        return getOrNull(key)
    }

    override fun hasKey(key: String): Boolean {
        return hashmap.containsKey(key)
    }

    override fun putBoolean(key: String, value: Boolean) {
        hashmap[key] = value
    }

    override fun putDouble(key: String, value: Double) {
        hashmap[key] = value
    }

    override fun putFloat(key: String, value: Float) {
        hashmap[key] = value
    }

    override fun putInt(key: String, value: Int) {
        hashmap[key] = value
    }

    override fun putLong(key: String, value: Long) {
        hashmap[key] = value
    }

    override fun putString(key: String, value: String) {
        hashmap[key] = value
    }

    override fun remove(key: String) {
        hashmap.remove(key)
    }
}
