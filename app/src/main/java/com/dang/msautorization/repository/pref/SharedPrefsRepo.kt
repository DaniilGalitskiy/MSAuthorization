package com.dang.msautorization.repository.pref

interface SharedPrefsRepo {
    val NAME_PREFS: String
        get() = "msAuthorization"

    val KEY_HOME_FLAG: String
        get() = "homeflag"

    fun setHomeFlag()
    fun isHomeFlag(): Boolean
}