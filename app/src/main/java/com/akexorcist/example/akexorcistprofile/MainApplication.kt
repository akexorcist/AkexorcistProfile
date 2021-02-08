package com.akexorcist.example.akexorcistprofile

import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.jakewharton.threetenabp.AndroidThreeTen

class MainApplication : SplitCompatApplication() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}