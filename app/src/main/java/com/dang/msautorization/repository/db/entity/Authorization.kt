package com.dang.msautorization.repository.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

data class AuthorizationResult(val id: Long, val token: String)

@Entity
data class AuthorizationUser(
        @PrimaryKey val id: Long,
        val name: String,
        val token: String
)