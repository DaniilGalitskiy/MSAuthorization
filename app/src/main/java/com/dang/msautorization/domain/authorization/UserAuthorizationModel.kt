package com.dang.msautorization.domain.authorization

import com.dang.msautorization.repository.db.entity.AuthorizationResult
import com.dang.msautorization.repository.net.model.UserLogin
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call


interface UserAuthorizationModel {

    fun setAuthorizationLogin(authorization: String, userLogin: UserLogin): Single<AuthorizationResult>

}