package com.example.experimentation

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.activity.result.ActivityResultRegistry
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.room.Room
import com.example.experimentation.API.RepositoryAPI
import com.example.experimentation.AuthScreen.ProfileViewModel
import com.example.experimentation.EditProfile.EditProfileViewModel
import com.example.experimentation.HomeFragment.HomeViewModel
import com.example.experimentation.MainActivity.MainActivityViewModel
import com.example.experimentation.room.DBprovider
import com.example.experimentation.room.DaoUser
import com.example.experimentation.room.Preference
import com.example.experimentation.room.RepositoryUser
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object
AppModule {
    @Singleton
    @Provides
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Singleton
    @Provides
    fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router

    @Singleton
    @Provides
    fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder =
        cicerone.getNavigatorHolder()

    @Singleton
    @Provides
    fun provideNavigator(act: Activity, cont: Int): AppNavigator =
        AppNavigator(act as FragmentActivity, cont)

    @Singleton
    @Provides
    fun provideRepositoryItems(): RepositoryItems = RepositoryItems()

    @Singleton
    @Provides
    fun provideRepositoryAPI(): RepositoryAPI = RepositoryAPI()

    @Singleton
    @Provides
    fun provideNotificationService(): NotificationService = NotificationService()

    @Singleton
    @Provides
    fun providePreference(context:Context): Preference = Preference(context)

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DBprovider {
        return Room.databaseBuilder(
            context,
            DBprovider::class.java,
            "mydatabase"
        ).build()
    }

    @Singleton
    @Provides
    fun provideHomeViewModel(
        repositoryItems: RepositoryItems,
        repositoryAPI: RepositoryAPI,
        router: Router,
        context: Application
    ): HomeViewModel = HomeViewModel(repositoryItems, repositoryAPI, router, context)

    @Singleton
    @Provides
    fun provideEditProfileViewModel(
        router: Router,
        context: Application,
        repositoryUser: RepositoryUser,
        preference: Preference
    ): EditProfileViewModel = EditProfileViewModel(router, context, repositoryUser, preference )

    @Singleton
    @Provides
    fun provideProfileViewModel(
        router: Router,
        repositoryAPI: RepositoryAPI,
        context: Application,
        repositoryUser: RepositoryUser,
        preference: Preference
    ): ProfileViewModel =
        ProfileViewModel(router, repositoryAPI, context, repositoryUser, preference )

    @Singleton
    @Provides
    fun provideMainActivityViewModel(router: Router): MainActivityViewModel =
        MainActivityViewModel(router)

    @Singleton
    @Provides
    fun provideDaoUser(dbProvider: DBprovider): DaoUser = dbProvider.DaoUser()


    @Singleton
    @Provides
    fun provideRepositoryUser(user: DaoUser): RepositoryUser = RepositoryUser(user)

}