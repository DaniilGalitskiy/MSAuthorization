package com.dang.msautorization.domain.user_info

import com.dang.msautorization.repository.db.entity.AuthorizationUser
import com.dang.msautorization.repository.db.entity.User
import io.reactivex.Completable
import io.reactivex.Observable

interface UserInfoModel {

    fun getCurrentUser(): Observable<User>

    fun getAllAuthorized(): Observable<List<AuthorizationUser>>

    fun updateUsersInfo(): Completable

}