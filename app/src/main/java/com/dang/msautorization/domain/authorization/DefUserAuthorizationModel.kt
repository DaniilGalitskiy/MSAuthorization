package com.dang.msautorization.domain.authorization

import com.dang.msautorization.repository.db.dao.UserDao
import com.dang.msautorization.repository.db.entity.AuthorizationResult
import com.dang.msautorization.repository.db.entity.AuthorizationUser
import com.dang.msautorization.repository.net.Api
import com.dang.msautorization.repository.net.model.UserLogin
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

class DefUserAuthorizationModel(private val db: UserDao, private val api: Api) :
        UserAuthorizationModel {

    override fun setAuthorizationLogin(authorization: String,
                                       userLogin: UserLogin,
                                       username: String): Single<AuthorizationResult> =
            api.loginUser(authorization, userLogin)
                    .doOnSuccess {
                        db.insertAuthorizationUser(
                                AuthorizationUser(
                                        id = it.id,
                                        token = it.token,
                                        name = username
                                )
                        )

                    }

    override fun isSignedUserByName(name: String): Single<Int> =
            db.isSignedUserByName(name)


    override fun isSignedUser(): Int = db.isSignedUser()
}