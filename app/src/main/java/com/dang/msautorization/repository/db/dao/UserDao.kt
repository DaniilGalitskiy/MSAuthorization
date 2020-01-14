package com.dang.msautorization.repository.db.dao

import androidx.room.*
import com.dang.msautorization.repository.db.entity.Authorization
import com.dang.msautorization.repository.db.entity.User
import com.dang.msautorization.repository.db.tuple.UserTuple
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT COUNT(id) FROM Authorization WHERE name like :name")
    fun isCheckSameSignedUserByName(name: String): Single<Boolean>


    @Query("SELECT * FROM Authorization")
    fun getAllAuthorizations(): Observable<List<Authorization>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthorizationUser(authorization: Authorization)


    @Transaction
    fun clearAndSetCurrentUserById(id: Long) {
        clearActiveAuthorizations()
        updateCurrentAuthorization(id)
    }

    @Query("UPDATE Authorization SET isActive = 1 WHERE id = :id")
    fun updateCurrentAuthorization(id: Long)

    @Query("UPDATE Authorization SET isActive = 0 WHERE isActive = 1")
    fun clearActiveAuthorizations()


    @Query(
            """SELECT Authorization.id as id, User.name as name, User.credential as credential, User.avatarUrl as avatar, Authorization.isActive as is_active
        FROM Authorization
        LEFT JOIN User ON Authorization.id = User.authorizationUserId"""
    )
    fun getAllUsers(): Observable<List<UserTuple>>


    @Transaction
    fun deleteCurrentUserAndSetNewCurrent(id: Long) {
        deleteUserById(id)
        updateFirstUserActive(getFirstId())
    }

    @Query("DELETE FROM Authorization WHERE id = :id")
    fun deleteUserById(id: Long)

    @Query("UPDATE Authorization SET isActive = 1 WHERE id = :firstId")
    fun updateFirstUserActive(firstId: Int)

    @Query("SELECT id FROM Authorization LIMIT 1")
    fun getFirstId(): Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)


    @Query("SELECT credential FROM User where authorizationUserId = :id")
    fun getCredentialById(id: Long): String

    @Query("SELECT isActive FROM Authorization where id = :id")
    fun getIsActiveById(id: Long): Boolean
}