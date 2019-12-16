package com.dang.msautorization.repository.db.dao

import androidx.room.*
import com.dang.msautorization.repository.db.entity.Authorization
import com.dang.msautorization.repository.db.entity.User
import com.dang.msautorization.repository.db.tuple.UserTuple
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT COUNT(id) FROM Authorization WHERE name like :name limit 1")
    fun isSignedUserByName(name: String): Single<Int>


    @Query("SELECT * FROM Authorization")
    fun getAllAuthorizations(): Observable<List<Authorization>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthorizationUser(authorization: Authorization)


    @Transaction
    fun clearAndSetCurrentUserById(id: Long) {
        updateClearAuthorizations()
        updateCurrentAuthorizations(id)
    }

    @Query("UPDATE Authorization SET isActive = 1 WHERE id = :id")
    fun updateCurrentAuthorizations(id: Long)

    @Query("UPDATE Authorization SET isActive = 0 WHERE isActive = 1")
    fun updateClearAuthorizations()


    @Query(
            """SELECT Authorization.id as id, User.name as name, User.avatarUrl as avatar, Authorization.isActive as is_active 
        FROM Authorization 
        LEFT JOIN User ON Authorization.id = User.authorizationUserId
        WHERE Authorization.isActive = 1"""
    )
    fun getCurrentUser(): Observable<UserTuple>

    @Query(
            """SELECT Authorization.id as id, User.name as name, User.avatarUrl as avatar, Authorization.isActive as is_active
        FROM Authorization
        LEFT JOIN User ON Authorization.id = User.authorizationUserId
        ORDER BY User.name"""
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
}