package com.example.experimentation

import com.example.experimentation.AuthScreen.ProfileFragment
import com.example.experimentation.EditProfile.EditProfileFragment
import com.example.experimentation.HomeFragment.HomeFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun routeToHome() = FragmentScreen { HomeFragment() }

    fun routeToProfile() = FragmentScreen { ProfileFragment() }

    fun routeToEdit() = FragmentScreen { EditProfileFragment() }

}