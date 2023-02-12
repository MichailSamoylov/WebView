package com.app.webview.app

import android.app.Application
import com.app.webview.activity.di.mainActivityModule
import com.app.webview.app.di.ciceroneModule
import com.app.webview.app.di.routersModule
import com.app.webview.screens.timersettings.di.timerSettingsModule
import com.app.webview.screens.timerwork.di.timerWorkModule
import com.app.webview.screens.trainings.di.trainingsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidLogger(Level.ERROR)
			androidContext(this@App)

			modules(
				ciceroneModule,
				routersModule,
				mainActivityModule,
				timerSettingsModule,
				timerWorkModule,
				trainingsModule,
			)
		}
	}
}