package com.dang.msautorization.domain.user_info

import com.dang.msautorization.repository.db.dao.UserDao
import com.dang.msautorization.repository.db.entity.AuthorizationUser
import com.dang.msautorization.repository.db.entity.User
import com.dang.msautorization.repository.net.Api
import io.reactivex.Observable

class DefUserInfoModel(private val db: UserDao, private val api: Api) : UserInfoModel {

    override fun getCurrentUser(): Observable<User> {
        return db.getCurrentUser()
    }

    override fun getAllAuthorized(): Observable<List<AuthorizationUser>> {
        return db.getAuthorizationUsers()
    }


    override fun updateUsersInfo() = db.getAuthorizationUsers()
            .flatMapIterable { users -> users }
            .switchMapSingle { authorizationUser ->
                api.userInfo(authorizationUser.token).map { authUser ->
                    authUser to authorizationUser.id
                }
            }
            .doOnNext { (userResult, id) ->
                db.insertUser(
                        User(
                                authorizationUserId = id,
                                name = userResult.login,
                                avatarUrl = userResult.avatarUrl
                        )
                )
            }.ignoreElements()!!

}