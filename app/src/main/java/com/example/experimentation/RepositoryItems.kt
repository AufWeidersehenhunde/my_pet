package com.example.experimentation

import dagger.Module
import dagger.assisted.AssistedInject
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class RepositoryItems @Inject constructor() {
   val items = listOf<Items>(Items("Basya", R.drawable.kids), Items("Add", R.drawable.plus)
       )
}