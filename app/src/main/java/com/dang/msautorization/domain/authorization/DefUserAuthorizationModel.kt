package com.dang.msautorization.domain.authorization

import com.dang.msautorization.repository.db.dao.UserDao
import com.dang.msautorization.repository.db.entity.Authorization
import com.dang.msautorization.repository.db.entity.AuthorizationResult
import com.dang.msautorization.repository.net.Api
import com.dang.msautorization.repository.net.model.UserLogin
import io.reactivex.Single

class DefUserAuthorizationModel(private val db: UserDao, private val api: Api) :
        UserAuthorizationModel {

    override fun setAuthorizationLogin(credential: String,
                                       userLogin: UserLogin,
                                       username: String): Single<AuthorizationResult> =
            api.loginUser(credential, userLogin)
                    .doOnSuccess {
                        db.updateClearAuthorizations()
                        db.insertAuthorizationUser(
                                Authorization(
                                        id = it.id,
                                        token = it.token,
                                        name = username,
                                        credential = credential
                                )
                        )

                    }

    override fun isSignedUserByName(name: String): Single<Int> =
            db.isSignedUserByName(name)
}