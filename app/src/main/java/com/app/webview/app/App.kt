package com.app.webview.app

import android.app.Application
import com.app.webview.activity.di.mainActivityModule
import com.app.webview.app.di.ciceroneModule
import com.app.webview.app.di.navigationModule
import com.app.webview.screens.stub.di.stubTimerModule
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
				navigationModule,
				mainActivityModule,
				stubTimerModule,
			)
		}
	}
}