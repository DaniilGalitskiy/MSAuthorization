package com.dang.msautorization.repository.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dang.msautorization.repository.db.entity.AuthorizationResult
import io.reactivex.Observable

@Dao
interface UserDao {

    @Insert
    fun insert(authorizationResult: AuthorizationResult)

    @Delete
    fun delete(authorizationResult: AuthorizationResult)
}