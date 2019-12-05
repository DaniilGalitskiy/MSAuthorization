package com.dang.msautorization.repository.net

import com.dang.msautorization.repository.db.entity.AuthorizationResult
import com.dang.msautorization.repository.db.entity.User
import com.dang.msautorization.repository.net.model.UserLogin
import io.reactivex.Single
import retrofit2.http.*

interface Api {

    companion object {
        const val CONNECT_TIMEOUT: Long = 2500
        const val URL: String = "https://api.github.com"
    }

    @POST("authorizations")
    fun loginUser(@Header("Authorization") authorization: String, @Body userLogin: UserLogin): Single<AuthorizationResult>

    @GET("user")
    fun userInfo(@Query("access_token") token: String): Single<User>
}