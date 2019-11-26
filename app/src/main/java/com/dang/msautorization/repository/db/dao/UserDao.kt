package com.dang.msautorization.repository.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dang.msautorization.repository.db.entity.Authorization
import io.reactivex.Observable

@Dao
interface UserDao {
    @Query("SELECT * FROM Authorization")
    fun getAll(): Observable<List<Authorization>>

    @Insert
    fun insert(authorization: Authorization)

    @Delete
    fun delete(authorization: Authorization)
}