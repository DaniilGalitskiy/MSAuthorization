package com.dang.msautorization.repository.pref

interface SharedPrefsScreen {
    companion object {
        val NAME_PREFS: String
            get() = "msAuthorization"

        val KEY_HOME: String
            get() = "home"
    }

    fun setHome()
    fun isHome(): Boolean
}