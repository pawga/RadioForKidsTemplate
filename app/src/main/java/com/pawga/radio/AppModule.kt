package com.pawga.radio

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.pawga.radio.data.Repository
import com.pawga.radio.data.RepositoryImpl
import com.pawga.radio.data.db.AppDatabase
import toothpick.config.Module

/**
 * Created by sivannikov
 */

class AppModule(val application: Application) : Module() {

    init {
        val context = application.applicationContext
        bind(Application::class.java)
                .toInstance(application)
        bind(Context::class.java)
                .toInstance(context)
        bind(SharedPreferences::class.java)
                .toInstance(PreferenceManager.getDefaultSharedPreferences(context))
        bind(InternalStorage::class.java)
                .toInstance(InternalStorage(context))
        bind(Repository::class.java)
                .to(RepositoryImpl::class.java)
                .singleton()
        bind(AppConfig::class.java)
                .to(AppConfigImpl::class.java)
                .singleton()
        bind(AppDatabase::class.java).
                toInstance(AppDatabase.getInstance(context))
    }
}