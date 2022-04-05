package com.albertomagalhaes.lordoftheringsinfo.application

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import android.app.Application as AndroidApplication

class Application: AndroidApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Application)
            modules(
                repositoryModule,
                useCaseModule,
                viewModelModule,
                daoModule
            )
        }
    }

}