package com.example.experimentation.HomeFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.experimentation.API.RepositoryAPI
import com.example.experimentation.API.RepositoryRemoteItemLatest
import com.example.experimentation.API.RepositoryRemoteItemSale
import com.example.experimentation.Items
import com.example.experimentation.RepositoryItems
import com.example.experimentation.Screens
import com.github.terrakok.cicerone.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repositoryItems: RepositoryItems,
    private val repositoryAPI: RepositoryAPI,
    private val router: Router,
    private val context: Application
) : ViewModel() {
    val ctx = context
    private val _items = MutableStateFlow<List<Items>?>(null)
    val items: MutableStateFlow<List<Items>?> = _items
    private val _latestItems = MutableStateFlow<List<RepositoryRemoteItemLatest>?>(null)
    val latestItems: MutableStateFlow<List<RepositoryRemoteItemLatest>?> = _latestItems
    private val _saleItems = MutableStateFlow<List<RepositoryRemoteItemSale>?>(null)
    val saleItems: MutableStateFlow<List<RepositoryRemoteItemSale>?> = _saleItems

    init {
        observeItems()
        observeLatestData()
    }

    private fun observeLatestData() {
        viewModelScope.launch {
            val last = repositoryAPI.getLatest()
            _latestItems.value = last.latest
            val sale = repositoryAPI.getSale()
            _saleItems.value = sale.flash_sale
        }
    }

    fun routeToProfile() {
        router.navigateTo(Screens.routeToProfile())
    }

    private fun observeItems() {
        _items.value = repositoryItems.items
    }
}