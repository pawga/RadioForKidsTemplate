package com.pawga.radio.ui.languages

import com.pawga.radio.ActivityViewModelScope
import com.pawga.radio.AppConfig
import com.pawga.radio.ApplicationScope
import com.pawga.radio.model.Language
import com.pawga.radio.model.RadioLanguage
import com.pawga.radio.common.SelectViewModel
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject

class SelectLanguagesViewModel : SelectViewModel<RadioLanguage>() {
    override val list = ArrayList<RadioLanguage>()

    val appConfig: AppConfig by inject()

    init {
        KTP.openScopes(ApplicationScope::class.java)
                .openSubScope(ActivityViewModelScope::class.java)
                .openSubScope(SelectLanguagesViewModel::class.java)
                .inject(this)

        val languagesList: List<RadioLanguage> = appConfig.languagesList.value ?: getDefaultList()
        list.addAll(languagesList)
    }

    private fun getDefaultList(): List<RadioLanguage> {
        return Language.values().map { RadioLanguage(it, false) }
    }

    fun saveLanguages() {
        appConfig.save(list)
    }
}