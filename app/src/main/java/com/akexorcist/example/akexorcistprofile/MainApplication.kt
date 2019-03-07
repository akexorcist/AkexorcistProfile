package com.akexorcist.example.akexorcistprofile

import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.jakewharton.threetenabp.AndroidThreeTen
import io.sentry.Sentry
import io.sentry.android.AndroidSentryClientFactory

class MainApplication : SplitCompatApplication() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        Sentry.init(AndroidSentryClientFactory(applicationContext))
    }
}