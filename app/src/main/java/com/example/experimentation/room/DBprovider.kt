package com.example.experimentation.room

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [UsersDb::class],
    version = 1
)
abstract class DBprovider: RoomDatabase() {
    abstract fun DaoUser(): DaoUser
}