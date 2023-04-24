package com.example.experimentation.MainActivity

import androidx.lifecycle.ViewModel
import com.example.experimentation.Screens
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class MainActivityViewModel @Inject constructor(
    private val router: Router
): ViewModel() {

    fun create(){
        router.newRootScreen(Screens.routeToHome())
    }

}