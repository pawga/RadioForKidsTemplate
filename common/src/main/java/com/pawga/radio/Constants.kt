package com.pawga.radio

/**
 * Created by sivannikov
 */

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope

@javax.inject.Scope
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ActivityScope

@javax.inject.Scope
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ActivityViewModelScope

const val LIST_LANGUAGES = "LIST_LANGUAGES"
const val CURRENT_THEME = "CURRENT_THEME"
const val DATABASE_NAME = "radio-db"
const val NOTIFICATION_LARGE_ICON_SIZE = 144 // px

