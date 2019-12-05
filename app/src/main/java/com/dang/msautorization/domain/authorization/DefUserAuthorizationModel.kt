package com.dang.msautorization.domain.authorization

import com.dang.msautorization.repository.db.dao.UserDao
import com.dang.msautorization.repository.net.Api
import com.dang.msautorization.repository.db.entity.AuthorizationResult
import com.dang.msautorization.repository.db.entity.AuthorizationUser
import com.dang.msautorization.repository.net.model.UserLogin
import io.reactivex.Observable
import io.reactivex.Single

class DefUserAuthorizationModel(private val db: UserDao, private val api: Api) :
        UserAuthorizationModel {

    override fun setAuthorizationLogin(authorization: String,
                                       userLogin: UserLogin,
                                       userName: String): Single<AuthorizationResult> {

        return api.loginUser(authorization, userLogin).doOnSuccess(db::insertSignedResult)
                .doOnSuccess {
                    db.insertSignedUser(AuthorizationUser(resultId = it.id, name = userName))
                }
    }

    override fun getSignedUserByNameCount(name: String): Observable<Int> {
        return db.getSignedUserByNameCount(name)
    }
}