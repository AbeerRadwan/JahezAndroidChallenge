package net.jahez.jahezchallenge.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import android.view.ContextThemeWrapper
import net.jahez.jahezchallenge.R
import java.util.*

class ContextUtils(base: Context) : ContextThemeWrapper(base, R.style.Theme_JahezChallenge) {
    companion object {

        fun wrap(context: Context, language: String): ContextThemeWrapper {
            var context = context
            val config = context.resources.configuration
            if (language != "") {
                val locale = Locale(language)
                Locale.setDefault(locale)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    setSystemLocale(config, locale)
                } else {
                    setSystemLocaleLegacy(config, locale)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    config.setLayoutDirection(locale)
                    context = context.createConfigurationContext(config)
                } else {
                    context.resources.updateConfiguration(config, context.resources.displayMetrics)
                }
            }
            return ContextUtils(context)
        }

        @SuppressWarnings("deprecation")
        fun setSystemLocaleLegacy(config: Configuration, locale: Locale) {
            config.locale = locale
        }

        @TargetApi(Build.VERSION_CODES.N)
        fun setSystemLocale(config: Configuration, locale: Locale) {
            config.setLocale(locale)
        }
    }
}