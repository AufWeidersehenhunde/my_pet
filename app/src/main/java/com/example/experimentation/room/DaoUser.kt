package com.example.experimentation.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.experimentation.HomeFragment.User
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Dao
interface DaoUser  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(User: UsersDb)

    @Query("UPDATE users SET name =:name, gender=:gender, date=:date, avatar=:avatar WHERE uuid=:uuid")
    fun change(uuid: String?, name: String, gender: String?, date: String?, avatar: String?)

    @Query("SELECT*FROM users WHERE uuid=:uuid")
    fun takeProfile(uuid: String): Flow<UsersDb>

}