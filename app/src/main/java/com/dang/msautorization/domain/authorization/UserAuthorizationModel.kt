package com.dang.msautorization.domain.authorization

import com.dang.msautorization.repository.db.entity.Authorization
import io.reactivex.Observable


interface UserAuthorizationModel {
    fun getAll(): Observable<List<Authorization>>
}