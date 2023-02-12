package com.app.webview.screens.timersettings.di

import com.app.webview.screens.timersettings.presentation.TimerSettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val timerSettingsModule = module {
	viewModel {
		TimerSettingsViewModel(
			router = get(),
			getUniqueNamePrefixUseCase = get()
		)
	}
}