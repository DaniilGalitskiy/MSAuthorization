package com.dang.msautorization.domain.user_info

import com.dang.msautorization.domain.user_info.entity.DynamicUser
import com.dang.msautorization.repository.db.dao.UserDao
import com.dang.msautorization.repository.db.entity.User
import com.dang.msautorization.repository.net.Api
import io.reactivex.Completable

class DefUserInfoModel(private val db: UserDao, private val api: Api) : UserInfoModel {


    override fun updateClearAndInsertAuthorizations() = db.getAllAuthorizations()
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

    override fun getAllUsers() = db.getAllUsers()/*.map { userList ->
        userList.map { user ->
            DynamicUser(
                    id = user.id,
                    name = user.name,
                    credential = user.credential,
                    avatar = user.avatar,
                    isActive = user.isActive
            )
        }
    }!!*/

    override fun updateClearAndSetCurrentUserById(id: Long) =
            Completable.fromAction { db.clearAndSetCurrentUserById(id) }

    override fun deleteUser(dynamicUser: DynamicUser): Completable =
            Completable.fromAction {
                api.deleteAuthorizationToken(
                        dynamicUser.credential!!, dynamicUser.id
                ).doOnComplete {
                    if (dynamicUser.isActive)
                        db.deleteCurrentUserAndSetNewCurrent(dynamicUser.id)
                    else
                        db.deleteUserById(dynamicUser.id)
                }.subscribe()
            }
}