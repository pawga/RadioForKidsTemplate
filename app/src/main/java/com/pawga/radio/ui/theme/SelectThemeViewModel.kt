package com.pawga.radio.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pawga.radio.ActivityViewModelScope
import com.pawga.radio.AppConfig
import com.pawga.radio.ApplicationScope
import com.pawga.radio.model.RadioTheme
import com.pawga.radio.model.Theme
import com.pawga.radio.common.SelectViewModel
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject

class SelectThemeViewModel : SelectViewModel<RadioTheme>() {
    override val list = ArrayList<RadioTheme>()

    val appConfig: AppConfig by inject()

    private val _savedNewTheme: MutableLiveData<Boolean> = MutableLiveData()
    val savedNewTheme: LiveData<Boolean> = _savedNewTheme

    init {
        KTP.openScopes(ApplicationScope::class.java)
                .openSubScope(ActivityViewModelScope::class.java)
                .openSubScope(SelectThemeViewModel::class.java)
                .inject(this)

        loadDefault()
        markCurrentTheme()
        _savedNewTheme.value = false
    }

    private fun markCurrentTheme() {
        val current = appConfig.currentTheme.value ?: Theme.FROG
        val item = list.find { it.theme == current }
        if (item != null) {
            item.select = true
        }
    }

    private fun loadDefault() {
        list.addAll(Theme.values().map { RadioTheme(it, false) })
    }

    fun saveTheme(theme: Theme) {
        appConfig.save(theme)
        _savedNewTheme.value = true
    }
}