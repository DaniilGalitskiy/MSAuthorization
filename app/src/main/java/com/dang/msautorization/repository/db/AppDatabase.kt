package com.dang.msautorization.repository.db

import com.dang.msautorization.repository.db.dao.UserDao

interface AppDatabase {
    fun getUserDao(): UserDao
}