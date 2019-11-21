package com.dang.msautorization.repository.pref

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class DefSharedPrefsRepo @Inject constructor(context: Context) : SharedPrefsRepo {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(NAME_PREFS, Context.MODE_PRIVATE)

    override fun isHomeFlag(): Boolean {
        return prefs.getBoolean(KEY_HOME_FLAG, false)
    }

    override fun setHomeFlag() {
        val editor = prefs.edit()
        editor.putBoolean(KEY_HOME_FLAG, true).apply()
    }
}