package com.pawga.radio.ui

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.pawga.radio.ActivityModule
import com.pawga.radio.ApplicationScope
import toothpick.Scope
import toothpick.ktp.KTP
import toothpick.smoothie.lifecycle.closeOnDestroy

/**
 * Created by sivannikov
 */

abstract class BaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    private fun getScope(): Scope {
        return KTP.openRootScope()
                .openSubScope(ApplicationScope::class.java)
                .openSubScope(this)
    }

    open fun KTPinject() {
        getScope()
                .installModules(ActivityModule(this))
                .closeOnDestroy(this)
                .inject(this)
    }
}