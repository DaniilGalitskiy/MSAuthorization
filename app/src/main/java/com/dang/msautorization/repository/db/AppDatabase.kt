package com.dang.msautorization.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dang.msautorization.repository.db.dao.UserDao
import com.dang.msautorization.repository.db.entity.AuthorizationResult
import com.dang.msautorization.repository.db.entity.AuthorizationUser

@Database(entities = [AuthorizationResult::class, AuthorizationUser::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

}