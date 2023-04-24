package com.example.experimentation.EditProfile

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.experimentation.Screens
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class EditProfileViewModel @Inject constructor(
    private val router: Router,
    private val context: Application
) : ViewModel() {
    val ctx = context
    fun routeToProfile() {
        router.backTo(Screens.routeToProfile())
    }
}