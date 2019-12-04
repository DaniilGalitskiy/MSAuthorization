package com.dang.msautorization.repository.db.dao

import androidx.room.*
import com.dang.msautorization.repository.db.entity.AuthorizationResult
import com.dang.msautorization.repository.db.entity.AuthorizationUser
import io.reactivex.Observable

@Dao
interface UserDao {

    @Query("SELECT COUNT(id) FROM AuthorizationUser WHERE name like '%'||:name||'%'")
    fun getSignedUserByNameCount(name: String): Observable<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSignedResult(authorizationResult: AuthorizationResult)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSignedUser(authorizationUser: AuthorizationUser)

    @Delete
    fun delete(authorizationResult: AuthorizationResult)
}