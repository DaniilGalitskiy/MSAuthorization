package com.dang.msautorization.repository.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dang.msautorization.domain.auth.UserAuthorizationModel
import io.reactivex.Observable

@Dao
interface AuthUserDao {
    @Query("SELECT * FROM Authorization")
    fun getAll(): Observable<List<UserAuthorizationModel>>

    @Query("SELECT * FROM Authorization WHERE name like '%'||:name||'%'")
    fun getPictureByName(name: String): Observable<UserAuthorizationModel>

    @Insert
    fun insert(authUser: UserAuthorizationModel)

    @Delete
    fun delete(authUser: UserAuthorizationModel)
}