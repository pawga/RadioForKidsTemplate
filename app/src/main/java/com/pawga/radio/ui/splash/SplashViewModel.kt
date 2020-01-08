package com.pawga.radio.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.pawga.radio.ActivityViewModelScope
import com.pawga.radio.AppConfig
import com.pawga.radio.ApplicationScope
import com.pawga.radio.ui.theme.SelectThemeViewModel
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject

class SplashViewModel : ViewModel() {
    val _needClose = MutableLiveData<Boolean>()
    val needClose: LiveData<Boolean> = _needClose
    val appConfig: AppConfig by inject()

    private val observerReadyToStart = Observer<Boolean> {
        _needClose.value = it
    }

    init {
        KTP.openScopes(ApplicationScope::class.java)
                .openSubScope(ActivityViewModelScope::class.java)
                .openSubScope(SelectThemeViewModel::class.java)
                .inject(this)

        appConfig.readyToStart.observeForever(observerReadyToStart)
    }

    override fun onCleared() {
        appConfig.readyToStart.removeObserver(observerReadyToStart)
        super.onCleared()
    }
}
