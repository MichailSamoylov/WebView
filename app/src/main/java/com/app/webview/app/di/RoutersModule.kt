package com.app.webview.app.di

import com.app.webview.app.navigation.TimerSettingsRouterImpl
import com.app.webview.app.navigation.TimerWorkRouterImpl
import com.app.webview.app.navigation.TrainingRouterImpl
import com.app.webview.screens.timersettings.presentation.TimerSettingsRouter
import com.app.webview.screens.timerwork.presentation.TimerWorkRouter
import com.app.webview.screens.trainings.presentation.TrainingRouter
import org.koin.dsl.module

val routersModule = module {
	factory<TimerSettingsRouter> { TimerSettingsRouterImpl(get()) }
	factory<TimerWorkRouter> { TimerWorkRouterImpl(get()) }
	factory<TrainingRouter> { TrainingRouterImpl(get()) }
}