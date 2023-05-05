package com.example.experimentation.room

class RepositoryUser(
    private val user: DaoUser
) {
    fun insertProfileData(User:UsersDb) = user.insertUser(User)

    fun change(uuid:String?, name:String, date:String?, gender:String?, avatar:String?) = user.change(uuid, name, gender, date, avatar)

    fun takeProfile(uuid: String) = user.takeProfile(uuid)
}