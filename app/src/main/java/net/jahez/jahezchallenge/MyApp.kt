package net.jahez.jahezchallenge

import android.app.Application
import android.content.Context
import net.jahez.jahezchallenge.utils.LocaleUtil
import net.jahez.jahezchallenge.utils.Storage

class MyApp: Application() {
    val storage : Storage by lazy {
        Storage(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleUtil.getLocalizedContext(base, Storage(base).getPreferredLocale()))
    }
}