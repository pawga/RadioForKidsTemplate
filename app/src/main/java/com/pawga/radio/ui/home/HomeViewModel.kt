package com.pawga.radio.ui.home

import androidx.lifecycle.ViewModel
import androidx.paging.Config
import androidx.paging.toLiveData
import com.pawga.radio.ActivityScope
import com.pawga.radio.ApplicationScope
import com.pawga.radio.data.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject

class HomeViewModel : ViewModel() {
    private val repository: Repository by inject()
    private val repositoryJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + repositoryJob)

    val allStations by lazy {
        repository.getAllStationsFactory().toLiveData(Config(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200))
    }

    init {
        KTP.openScopes(ApplicationScope::class.java)
                .openSubScope(ActivityScope::class.java)
                .openSubScope(HomeViewModel::class.java)
                .inject(this)
    }

}