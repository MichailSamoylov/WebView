package com.app.webview.screens.timerwork.di

import com.app.webview.components.stub.trainings.doamin.entity.TrainingEntity
import com.app.webview.screens.timerwork.presentation.TimerWorkViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val timerWorkModule = module {
	viewModel { (trainingDate: TrainingEntity) ->
		TimerWorkViewModel(
			trainingDate = trainingDate,
			workTime = trainingDate.timerData,
			router = get(),
			saveTrainingUseCase = get()
		)
	}
}