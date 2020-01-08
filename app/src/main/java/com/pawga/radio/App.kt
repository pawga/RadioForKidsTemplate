package com.pawga.radio

import android.app.Application
import androidx.multidex.MultiDex
import com.facebook.stetho.Stetho
import timber.log.Timber
import toothpick.configuration.Configuration
import toothpick.ktp.KTP

/**
 * Created by sivannikov
 */


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        initializeTimber()
        initializeToothpick()
        initializeStetho()
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeToothpick() {

        if (BuildConfig.DEBUG) {
            KTP.setConfiguration(Configuration.forDevelopment())
            KTP.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        }

        KTP.openRootScope()
                .openSubScope(ApplicationScope::class.java)
                .installModules(AppModule(this))
                .inject(this)
    }

    private fun initializeStetho() {
        //Stetho.initializeWithDefaults(this)
        if (!BuildConfig.DEBUG) {
            return
        }
        val initializerBuilder = Stetho.newInitializerBuilder(this)
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        )
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        )
        Stetho.initialize(initializerBuilder.build())
    }
}