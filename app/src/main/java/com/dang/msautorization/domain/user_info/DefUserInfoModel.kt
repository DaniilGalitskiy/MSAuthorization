package com.dang.msautorization.domain.user_info

import com.dang.msautorization.repository.db.dao.UserDao
import com.dang.msautorization.repository.db.entity.User
import com.dang.msautorization.repository.net.Api
import io.reactivex.Completable

class DefUserInfoModel(private val db: UserDao, private val api: Api) : UserInfoModel {

    override fun reloadAuthorizations() = db.getAllAuthorizations()
            .flatMapIterable { users -> users }
            .switchMapSingle { authorizationUser ->
                api.userInfo(authorizationUser.token).map { authUser ->
                    Triple(authUser, authorizationUser.id, authorizationUser.credential)
                }
            }
            .doOnNext { (userResult, id, credential) ->
                db.insertUser(
                        User(
                                authorizationUserId = id,
                                credential = credential,
                                name = userResult.login,
                                avatarUrl = userResult.avatarUrl
                        )
                )
            }.ignoreElements()!!

    override fun getAllUsers() = db.getAllUsers()

    override fun changeCurrentUserById(id: Long) =
            Completable.fromAction { db.clearAndSetCurrentUserById(id) }

    override fun deleteUser(id: Long): Completable =
            Completable.fromAction {
                api.deleteAuthorizationToken(
                        db.getCredentialById(id), id
                ).doOnComplete {
                    if (db.getIsActiveById(id))
                        db.deleteCurrentUserAndSetNewCurrent(id)
                    else
                        db.deleteUserById(id)
                }.subscribe()
            }
}