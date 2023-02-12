package com.app.webview.screens.timerwork.di

import com.app.webview.components.stub.domain.TimerData
import com.app.webview.screens.timerwork.presentation.TimerWorkViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val timerWorkModule = module {
	viewModel { (workTime: TimerData) ->
		TimerWorkViewModel(
			workTime = workTime,
			router = get()
		)
	}
}