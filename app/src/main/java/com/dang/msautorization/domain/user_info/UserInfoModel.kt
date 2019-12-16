package com.dang.msautorization.domain.user_info

import com.dang.msautorization.domain.user_info.entity.DynamicUser
import io.reactivex.Completable
import io.reactivex.Observable

interface UserInfoModel {

    fun getCurrentUser(): Observable<DynamicUser>

    fun getAllUsers(): Observable<List<DynamicUser>>

    fun updateClearAndInsertAuthorizations(): Completable

    fun updateClearAndSetCurrentUserById(id: Long): Completable

    fun deleteUserById(dynamicUser: DynamicUser): Completable
}
