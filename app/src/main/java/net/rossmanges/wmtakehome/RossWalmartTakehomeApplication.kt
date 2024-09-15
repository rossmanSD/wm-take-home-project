package net.rossmanges.wmtakehome

import android.app.Application
import net.rossmanges.wmtakehome.di.appModule
import org.koin.core.context.GlobalContext.startKoin


class RossWalmartTakehomeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule)
        }
    }
}