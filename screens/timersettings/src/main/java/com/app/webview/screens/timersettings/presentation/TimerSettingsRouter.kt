package com.app.webview.screens.timersettings.presentation

import com.app.webview.components.stub.trainings.doamin.entity.TrainingEntity

interface TimerSettingsRouter {

	fun navigateToTrainingsScreen()
	fun navigateToWorkScreen(trainingEntity: TrainingEntity)
}