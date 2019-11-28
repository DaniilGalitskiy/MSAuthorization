package com.dang.msautorization.repository.net

import com.dang.msautorization.repository.db.entity.Authorization
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {

    companion object {
        const val CONNECT_TIMEOUT: Long = 2500
        const val URL: String = "https://api.github.com"
    }

    @GET("login")
    fun getAuthorization(@Header("Authorization") authHeader: String): Call<Authorization>
}