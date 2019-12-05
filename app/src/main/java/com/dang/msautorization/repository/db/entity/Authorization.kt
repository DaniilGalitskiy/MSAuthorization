package com.dang.msautorization.repository.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class AuthorizationResult(val id: Long, val token: String)

@Entity
data class AuthorizationUser(
        @PrimaryKey val id: Long,
        val name: String,
        val token: String,
        @SerializedName("is_active")
        val isActive: Boolean = true
)