package com.example.experimentation.AuthScreen

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.experimentation.API.RepositoryAPI
import com.example.experimentation.API.RepositoryRemoteItemLatest
import com.example.experimentation.API.RepositoryRemoteItemSale
import com.example.experimentation.Screens
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel

class ProfileViewModel @Inject constructor(
   private val router: Router,
   private val repositoryAPI: RepositoryAPI,
   private val context: Application,
) : ViewModel() {
    private val _latestItems = MutableStateFlow<List<RepositoryRemoteItemLatest>?>(null)
    val latestItems : MutableStateFlow<List<RepositoryRemoteItemLatest>?> = _latestItems
    val ctn = context

    init {
        observeLatestData()
    }
     fun routeToHome(){
         router.newRootScreen(Screens.routeToHome())
     }

    private fun observeLatestData() {
        viewModelScope.launch {
            val last = repositoryAPI.getLatest()
            _latestItems.value = last.latest
        }
    }

    fun routeToEdit(){
        router.navigateTo(Screens.routeToEdit())
    }

}





////    private fun mapGoogleException(throwable: Throwable): Throwable? {
////        if (throwable !is ApiException) {
////            return throwable
////        }
////        val message = when (throwable.statusCode) {
////            GoogleSignInStatusCodes.SIGN_IN_CANCELLED,
////            GoogleSignInStatusCodes.CANCELED -> null
////            else -> GoogleSignInStatusCodes.getStatusCodeString(throwable.statusCode)
////        }
////        return message?.let {
////            ApiException(Status(throwable.statusCode, it))
////        }
////    }