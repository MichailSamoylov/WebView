package com.app.webview.screens.stub.di

import com.app.webview.screens.stub.presentation.StubTimerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val stubTimerModule = module {
	viewModel {
		StubTimerViewModel()
	}
}