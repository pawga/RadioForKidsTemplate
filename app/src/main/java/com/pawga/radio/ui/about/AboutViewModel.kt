package com.pawga.radio.ui.about

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.core.content.pm.PackageInfoCompat
import androidx.lifecycle.ViewModel
import com.pawga.radio.ActivityScope
import com.pawga.radio.AppConfig
import com.pawga.radio.ApplicationScope
import com.pawga.radio.R
import toothpick.ktp.KTP
import toothpick.ktp.delegate.inject


class AboutViewModel : ViewModel() {

    val version: String
    val numberBuild: String
    val countStationInfo: String

    private val context: Context by inject()
    private val appConfig: AppConfig by inject()

    init {
        KTP.openScopes(ApplicationScope::class.java)
                .openSubScope(ActivityScope::class.java)
                .openSubScope(AboutViewModel::class.java)
                .inject(this)

        val packageManager: PackageManager = context.packageManager

        val info: PackageInfo = packageManager.getPackageInfo(context.packageName, 0)
        version = context.getText(R.string.str_version).toString() + ": " + info.versionName

        val longVersionCode = PackageInfoCompat.getLongVersionCode(info)
        val versionCode = longVersionCode.toInt()
        numberBuild = context.getText(R.string.str_build).toString() + ": " + versionCode
        countStationInfo = context.getText(R.string.str_count_station).toString() + ": " + getCountStation().toString()
    }

    private fun getCountStation(): Long {
        return appConfig.countDbItems
    }
}
