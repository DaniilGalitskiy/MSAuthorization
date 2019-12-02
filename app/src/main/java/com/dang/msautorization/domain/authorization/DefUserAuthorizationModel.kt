package com.dang.msautorization.domain.authorization

import com.dang.msautorization.repository.db.dao.UserDao
import com.dang.msautorization.repository.net.Api
import com.dang.msautorization.repository.db.entity.AuthorizationResult
import com.dang.msautorization.repository.net.model.UserLogin
import io.reactivex.Single
import retrofit2.Call

class DefUserAuthorizationModel(private val db: UserDao, private val api: Api) : UserAuthorizationModel {

    override fun setAuthorizationLogin(authorization: String, userLogin: UserLogin): Single<AuthorizationResult> {
        return api.loginUser(authorization, userLogin)
    }

}