package com.dang.msautorization.domain.authorization

import com.dang.msautorization.repository.db.entity.AuthorizationResult
import com.dang.msautorization.repository.net.model.UserLogin
import io.reactivex.Observable
import io.reactivex.Single


interface UserAuthorizationModel {

    fun setAuthorizationLogin(authorization: String, userLogin: UserLogin): Single<AuthorizationResult>
    fun getSignedUserByNameCount(name: String): Observable<Int>
}