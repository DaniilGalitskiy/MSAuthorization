package com.dang.msautorization.repository.pref

import android.content.Context
import android.content.SharedPreferences

class DefSharedPrefsScreen(context: Context) : SharedPrefsScreen {

    companion object {
        const val NAME_PREFS: String = "msAuthorization"

        const val KEY_HOME: String = "home"
    }

    private val prefs: SharedPreferences =
            context.getSharedPreferences(NAME_PREFS, Context.MODE_PRIVATE)

    override var isHome: Boolean
        get() = prefs.getBoolean(KEY_HOME, false)
        set(value) {
            val editor = prefs.edit()
            editor.putBoolean(KEY_HOME, value).apply()
        }
}