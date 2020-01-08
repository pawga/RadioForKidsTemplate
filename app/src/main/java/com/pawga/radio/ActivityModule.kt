package com.pawga.radio

import android.app.Activity
import android.content.Context
import toothpick.config.Module

/**
 * Created by sivannikov
 */

class ActivityModule(activity: Activity) : Module() {

    init {
        bind(Context::class.java).toInstance(activity)
    }
}