package com.dang.msautorization.repository.net

import com.dang.msautorization.repository.db.entity.AuthorizationResult
import com.dang.msautorization.repository.db.entity.UserResult
import com.dang.msautorization.repository.net.model.LoginBody
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface Api {

    companion object {
        const val CONNECT_TIMEOUT: Long = 2500
        const val URL: String = "https://api.github.com"
    }

    @POST("authorizations")
    fun loginUser(@Header("Authorization") authorization: String, @Body loginBody: LoginBody = LoginBody()): Single<AuthorizationResult>

    @GET("user")
    fun userInfo(@Query("access_token") token: String): Single<UserResult>

    @DELETE("authorizations/{id}")
    fun deleteAuthorizationToken(@Header("Authorization") authorization: String, @Path("id") token: Long): Completable
}