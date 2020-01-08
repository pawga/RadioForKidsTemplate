package com.pawga.radio.data

import android.content.Context
import android.content.SharedPreferences
import androidx.paging.DataSource
import com.pawga.radio.ApplicationScope
import com.pawga.radio.CURRENT_THEME
import com.pawga.radio.InternalStorage
import com.pawga.radio.LIST_LANGUAGES
import com.pawga.radio.data.db.AppDatabase
import com.pawga.radio.data.db.RadioStationDBItem
import com.pawga.radio.data.db.RadioStationItem
import com.pawga.radio.data.db.SreamItem
import com.pawga.radio.data.model.JsonRadioStationItem
import com.pawga.radio.model.RadioLanguage
import com.pawga.radio.model.Theme
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber
import toothpick.InjectConstructor
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject
import java.util.*


/**
 * Created by sivannikov
 */

interface Repository {
    fun save(languages: ArrayList<RadioLanguage>)
    fun getLanguages(): ArrayList<RadioLanguage>?

    fun save(theme: Theme)
    fun getTheme(): Theme?

    fun loadJsonFromAsset(source: String): String?

    fun loadRadioStationItemsFromAsset(source: String): List<JsonRadioStationItem>?

    fun saveToDb(list: List<JsonRadioStationItem>)
    fun getCountDbItems(): Long

    fun getAllStationsFactory(): DataSource.Factory<Int, RadioStationItem>
}


@InjectConstructor
class RepositoryImpl(private val sharedPreferences: SharedPreferences,
                        private val internalStorage: InternalStorage) : Repository {

    private val context: Context by inject()
    private val database: AppDatabase by inject()

    private val repositoryJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + repositoryJob)
    private val ioScope = CoroutineScope(Dispatchers.IO + repositoryJob)

    init {
        KTP.openScopes(ApplicationScope::class.java, this)
                .inject(this)
    }

    override fun save(languages: ArrayList<RadioLanguage>) {
        try {
            internalStorage.writeObject(LIST_LANGUAGES, languages)
        } catch (exp: Exception) {
            Timber.e(exp.toString())
        }
    }

    override fun save(theme: Theme) {
        try {
            internalStorage.writeObject(CURRENT_THEME, theme)
        } catch (exp: Exception) {
            Timber.e(exp.toString())
        }
    }

    override fun getLanguages(): ArrayList<RadioLanguage>? {
        return try {
            internalStorage.readObject(LIST_LANGUAGES) as ArrayList<RadioLanguage>
        } catch (exp: Exception) {
            Timber.e(exp.toString())
            null
        }
    }

    override fun getTheme(): Theme? {
        return try {
            internalStorage.readObject(CURRENT_THEME) as Theme?
        } catch (exp: Exception) {
            Timber.e(exp.toString())
            null
        }
    }

    override fun loadJsonFromAsset(source: String): String? {
        return try {
            context.assets.open(source).bufferedReader().use {
                it.readText()
            }
        } catch (exp: Exception) {
            null // no asset
        }
    }

    override fun loadRadioStationItemsFromAsset(source: String): List<JsonRadioStationItem>? {
        val moshi: Moshi = Moshi.Builder()
                .build()
        val listType = Types.newParameterizedType(
                MutableList::class.java,
                JsonRadioStationItem::class.java)
        val adapter: JsonAdapter<List<JsonRadioStationItem>> = moshi.adapter(listType)

        try {
            loadJsonFromAsset(source)?.let {
                return adapter.fromJson(it)
            }
        } catch (e: Exception) {
            Timber.i("Bad format json file")
        }

        return null
    }

    override fun saveToDb(list: List<JsonRadioStationItem>) {
        ioScope.apply {
            val stationsDao = database.radioStationDao()
            val streamDao = database.streamDBDao()

            stationsDao.deleteAll()
            streamDao.deleteAll()

            val listRadioStations1 = list.map {
                RadioStationDBItem(0, it.description, it.id, it.image, it.locale, it.name, it.type,null)
            }
            val codes = stationsDao.insertAllRadioStationItems(listRadioStations1)

            val streamsList = mutableListOf<SreamItem>()
            for ((index, value) in list.withIndex()) {
                if (value.streams != null) {
                    val valueStreams = value.streams.map {
                        SreamItem(0, codes[index], it.bitrate, it.id, it.stream)
                    }
                    streamsList.addAll(valueStreams)
                }
            }
            streamDao.insertAll(streamsList)
        }
    }

    override fun getCountDbItems(): Long {
        return database.radioStationDao().getCount()
    }

    override fun getAllStationsFactory(): DataSource.Factory<Int, RadioStationItem> {
        return database.radioStationDao().getAllStationsFactory()
    }
}