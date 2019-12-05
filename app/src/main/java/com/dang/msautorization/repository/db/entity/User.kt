package com.dang.msautorization.repository.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
        indices = [Index(value = ["authorizationUserId"], unique = true)],
        foreignKeys = [ForeignKey(
                entity = AuthorizationUser::class,
                parentColumns = ["id"],
                childColumns = ["authorizationUserId"],
                onDelete = ForeignKey.CASCADE
        )]
)
data class User(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        @SerializedName("authorization_user_id")
        val authorizationUserId: Long = 0,
        val name: String,
        @SerializedName("avatar_url")
        val avatarUrl: String,
        @SerializedName("is_active")
        val isActive: Boolean = true
)