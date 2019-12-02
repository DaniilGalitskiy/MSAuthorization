package com.dang.msautorization.repository.net

import com.dang.msautorization.repository.db.entity.AuthorizationResult
import com.dang.msautorization.repository.net.model.UserLogin
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {

    companion object {
        const val CONNECT_TIMEOUT: Long = 2500
        const val URL: String = "https://api.github.com"
    }

    //    @Header("Authorizations") credentials: String,
    @POST("authorizations")
    fun loginUser(@Header("Authorization") authorization: String, @Body userLogin: UserLogin): Single<AuthorizationResult>
}