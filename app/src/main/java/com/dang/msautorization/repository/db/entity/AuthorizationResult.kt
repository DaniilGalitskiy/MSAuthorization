package com.dang.msautorization.repository.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity
data class AuthorizationResult(
        @PrimaryKey val id: Long,
        val token: String
)

@Entity(
        foreignKeys = [ForeignKey(
                entity = AuthorizationResult::class,
                parentColumns = ["id"],
                childColumns = ["userId"],
                onDelete = CASCADE
        )]
)
data class AuthorizationUser(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val resultId: Long = 0,
        val name: String,
        val isActive: Boolean = true
)