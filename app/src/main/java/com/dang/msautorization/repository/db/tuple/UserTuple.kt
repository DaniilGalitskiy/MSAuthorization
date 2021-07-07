package com.dang.msautorization.repository.db.tuple

import androidx.room.ColumnInfo

data class UserTuple(
        @ColumnInfo(name = "id") val id: Long,
        @ColumnInfo(name = "name") val name: String?,
        @ColumnInfo(name = "credential") val credential: String?,
        @ColumnInfo(name = "avatar") val avatar: String?,
        @ColumnInfo(name = "is_active") val isActive: Boolean
)