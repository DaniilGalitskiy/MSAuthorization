package com.dang.msautorization.domain.authorization

import io.reactivex.Completable
import io.reactivex.Single


interface UserAuthorizationModel {

    fun login(credential: String, username: String): Completable


    fun isCheckSameSignedUserByName(name: String): Single<Boolean>
}