package com.dang.msautorization.domain.authorization

import com.dang.msautorization.repository.db.dao.UserDao
import com.dang.msautorization.repository.net.Api
import com.dang.msautorization.repository.db.entity.Authorization
import io.reactivex.Observable

class DefUserAuthorizationModel(userDao: UserDao, api: Api) : UserAuthorizationModel {

    val db = userDao
    val api = api

    override fun getAll(): Observable<List<Authorization>> {
        return db.getAll()
    }

}