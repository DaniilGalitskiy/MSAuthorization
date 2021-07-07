package com.dang.msautorization.repository.net.model

import com.dang.msautorization.BuildConfig

class LoginBody(val note: String = "MSAuth",
                val scopes: Array<String> = arrayOf("repo", "user"),
                val client_secret: String = BuildConfig.CLIENT_SECRET,
                val client_id: String = BuildConfig.CLIENT_ID)