package com.dang.msautorization.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dang.msautorization.repository.db.entity.AuthorizationUser
import com.dang.msautorization.repository.db.entity.User
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT COUNT(id) FROM AuthorizationUser WHERE name like '%'||:name||'%'")
    fun getSignedUserByNameCount(name: String): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthorizationUser(authorizationUser: AuthorizationUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(authorizationUser: User)

}