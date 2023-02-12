package com.app.webview.app.di

import com.app.webview.app.navigation.TimerSettingsRouterImpl
import com.app.webview.app.navigation.TimerWorkRouterImpl
import com.app.webview.screens.timersettings.presentation.TimerSettingsRouter
import com.app.webview.screens.timerwork.presentation.TimerWorkRouter
import org.koin.dsl.module

val navigationModule = module {
	factory<TimerSettingsRouter> { TimerSettingsRouterImpl(get()) }
	factory<TimerWorkRouter> { TimerWorkRouterImpl(get()) }
}