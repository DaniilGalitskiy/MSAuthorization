package com.dang.msautorization.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dang.msautorization.repository.db.dao.UserDao
import com.dang.msautorization.repository.db.entity.Authorization

@Database(entities = [Authorization::class], version = 1, exportSchema = false)
abstract class DefAppDatabase : RoomDatabase(), AppDatabase {

    abstract override fun getUserDao(): UserDao

}