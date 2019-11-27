package com.dang.msautorization.repository.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Authorization(
        @PrimaryKey val id: Int,
        val name: String,
        val token: String,
        val isActive: Boolean = true
)