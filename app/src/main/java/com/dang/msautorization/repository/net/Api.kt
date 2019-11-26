package com.dang.msautorization.repository.net

import com.dang.msautorization.repository.db.entity.Authorization
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Api {

//    @POST("users/DaniilGalitskiy")
//    fun getPicture(): Call<Authorization>

    @GET("basic")
    fun getAuthorization(@Header("Authorization")authHeader: String): Call<Authorization>

}