package com.pawga.radio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pawga.radio.data.Repository
import com.pawga.radio.model.Language
import com.pawga.radio.model.RadioLanguage
import com.pawga.radio.model.Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import toothpick.InjectConstructor
import toothpick.ktp.KTP

/**
 * Created by sivannikov
 */

interface AppConfig {
    val currentTheme: LiveData<Theme>
    val languagesList: LiveData<ArrayList<RadioLanguage>>
    val readyToStart: LiveData<Boolean>
    val countDbItems: Long

    fun save(languages: ArrayList<RadioLanguage>)
    fun save(theme: Theme)
}

@InjectConstructor
class AppConfigImpl(private val repository: Repository) : AppConfig {

    private val _currentTheme = MutableLiveData<Theme>()
    override val currentTheme: LiveData<Theme> = _currentTheme

    private val _languagesList = MutableLiveData<ArrayList<RadioLanguage>>()
    override val languagesList: LiveData<ArrayList<RadioLanguage>> = _languagesList

    private val _readyToStart = MutableLiveData<Boolean>()
    override val readyToStart = _readyToStart

    private var _countDbItems = 0L
    override val countDbItems: Long
        get() = _countDbItems

    private val appConfigImplJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + appConfigImplJob)
    private val ioScope = CoroutineScope(Dispatchers.IO + appConfigImplJob)

    init {
        KTP.openScopes(ApplicationScope::class.java, this)
                .inject(this)

        _currentTheme.value = getTheme()
        _languagesList.value = getLanguages()
        _readyToStart.value = false

        ioScope.launch {
            //delay(1000)

            _countDbItems = repository.getCountDbItems()

            if (_countDbItems == 0L) {
                // here load stations from cloud. Now - debug
                repository.loadRadioStationItemsFromAsset("mock_data_radio_station.json")?.let {
                    repository.saveToDb(it)
                }
                _countDbItems = repository.getCountDbItems()
            }

            uiScope.launch(context = Dispatchers.Main) {
                _readyToStart.value = true
            }
        }
    }

    override fun save(languages: ArrayList<RadioLanguage>) {
        repository.save(languages)
        _languagesList.value = languages
    }

    override fun save(theme: Theme) {
        repository.save(theme)
        _currentTheme.value = theme
    }

    private fun getLanguages(): ArrayList<RadioLanguage> {
        return repository.getLanguages() ?: getDefaultLanguages()
    }

    private fun getTheme(): Theme {
       return repository.getTheme() ?: Theme.FROG
    }

    private fun getDefaultLanguages(): ArrayList<RadioLanguage> {
        val arrayList = ArrayList<RadioLanguage>()
        arrayList.addAll(Language.values().map { RadioLanguage(it, false) })
        return arrayList
    }
}