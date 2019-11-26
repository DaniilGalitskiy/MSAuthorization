package com.dang.msautorization.repository.pref

import android.content.Context
import android.content.SharedPreferences
import com.dang.msautorization.repository.pref.SharedPrefsScreen.Companion.KEY_HOME
import com.dang.msautorization.repository.pref.SharedPrefsScreen.Companion.NAME_PREFS

class DefSharedPrefsScreen(context: Context) : SharedPrefsScreen {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(NAME_PREFS, Context.MODE_PRIVATE)

    override fun isHome(): Boolean {
        return prefs.getBoolean(KEY_HOME, false)
    }

    override fun setHome() {
        val editor = prefs.edit()
        editor.putBoolean(KEY_HOME, true).apply()
    }
}