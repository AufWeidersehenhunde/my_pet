package com.example.experimentation.EditProfile

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.experimentation.HomeFragment.User
import com.example.experimentation.Screens
import com.example.experimentation.room.Preference
import com.example.experimentation.room.RepositoryUser
import com.example.experimentation.room.UsersDb
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class EditProfileViewModel @Inject constructor(
    private val router: Router,
    private val context: Application,
    private val repositoryUser: RepositoryUser,
    private val preference: Preference
) : ViewModel() {
    val ctx = context

    private val _userData = MutableStateFlow<UsersDb?>(null)
    val userData: MutableStateFlow<UsersDb?> = _userData
    val pref = preference.getValue("pref")

    fun routeToProfile() {
        router.backTo(Screens.routeToProfile())
    }

    fun registration(user: UsersDb) {
        viewModelScope.launch(Dispatchers.IO) {
            if (preference.getValue("pref").isNullOrEmpty()) {
                repositoryUser.insertProfileData(user)
                preference.save("pref", "main").toString()
            } else {
                repositoryUser.change(user.uuid, user.name, user.date, user.gender, user.avatar)
            }
        }
    }

    fun takeProfile(uuid: String) {
        repositoryUser.takeProfile(uuid).onEach {
            _userData.value = it
        }.launchIn(viewModelScope)
    }

}