package com.dang.msautorization.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dang.msautorization.repository.db.entity.AuthorizationUser
import com.dang.msautorization.repository.db.entity.User
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT COUNT(id) FROM AuthorizationUser WHERE name like :name limit 1")
    fun isSignedUserByName(name: String): Single<Int>

    @Query("SELECT * FROM AuthorizationUser")
    fun getAuthorizationUsers(): Observable<List<AuthorizationUser>>

    @Query("UPDATE AuthorizationUser SET isActive = 0 WHERE isActive = 1")
    fun updateNotAuthorizedUser()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthorizationUser(authorizationUser: AuthorizationUser)


    @Query("SELECT * FROM User INNER JOIN AuthorizationUser ON User.authorizationUserId = AuthorizationUser.id WHERE AuthorizationUser.isActive = 1")
    fun getCurrentUser(): Observable<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

}