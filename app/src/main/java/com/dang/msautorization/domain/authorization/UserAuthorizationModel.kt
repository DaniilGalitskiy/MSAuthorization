package com.dang.msautorization.domain.authorization

import com.dang.msautorization.repository.db.entity.AuthorizationResult
import com.dang.msautorization.repository.net.model.UserLogin
import io.reactivex.Single


interface UserAuthorizationModel {

    fun setAuthorizationLogin(authorization: String,
                              userLogin: UserLogin, username: String): Single<AuthorizationResult>

    fun isSignedUserByName(name: String): Single<Int>

    fun isSignedUser(): Int
}