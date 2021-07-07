package com.dang.msautorization.domain.authorization

import com.dang.msautorization.repository.db.dao.UserDao
import com.dang.msautorization.repository.db.entity.Authorization
import com.dang.msautorization.repository.net.Api
import io.reactivex.Completable
import io.reactivex.Single

class DefUserAuthorizationModel(private val db: UserDao, private val api: Api) :
        UserAuthorizationModel {

    override fun login(credential: String, username: String): Completable =
            api.loginUser(credential)
                    .doOnSuccess {
                        db.clearActiveAuthorizations()
                        db.insertAuthorizationUser(
                                Authorization(
                                        id = it.id,
                                        token = it.token,
                                        name = username,
                                        credential = credential
                                )
                        )
                    }.ignoreElement()


    override fun isCheckSameSignedUserByName(name: String): Single<Boolean> =
            db.isCheckSameSignedUserByName(name)
}