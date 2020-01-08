package com.pawga.radio.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.pawga.radio.AppConfig
import com.pawga.radio.R
import com.pawga.radio.extensions.styleResource
import com.pawga.radio.model.Theme
import timber.log.Timber
import toothpick.ktp.delegate.inject

class RadioforChildrenActivity : BaseActivity(R.layout.activity_radiofor_children) {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val appConfig: AppConfig by inject()

    override fun onCreate(savedInstanceState: Bundle?) {

        KTPinject()

        val currTheme = appConfig.currentTheme.value ?: Theme.FROG
        setTheme(currTheme.styleResource)

        super.onCreate(savedInstanceState)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_favorites,
                R.id.navigation_stations,
                R.id.navigation_fairy_tales,
                R.id.navigation_auditions,
                R.id.navigation_songs,
                R.id.navigation_cartoons,
                R.id.navigation_nanny,
                R.id.navigation_select_languages,
                R.id.navigation_select_theme,
                R.id.navigation_about,
                R.id.nav_home
                ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.radiofor_children, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_close -> {
                finish()
                return true
            }
            R.id.action_about -> {
                findNavController(R.id.nav_host_fragment)
                        .navigate(R.id.navigation_about)
                return true
            }
            R.id.action_exit -> {
                stopMedia()
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun stopMedia() {
        Timber.i("stopMedia")
    }
}
