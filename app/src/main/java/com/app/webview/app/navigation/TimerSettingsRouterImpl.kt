package com.app.webview.app.navigation

import com.app.webview.components.stub.trainings.doamin.entity.TrainingEntity
import com.app.webview.screens.timersettings.presentation.TimerSettingsRouter
import com.app.webview.screens.timerwork.getTimerWorkScreen
import com.app.webview.screens.trainings.getTrainingsScreen
import com.github.terrakok.cicerone.Router

class TimerSettingsRouterImpl(private val router: Router) : TimerSettingsRouter {

	override fun navigateToTrainingsScreen() {
		router.navigateTo(getTrainingsScreen())
	}

	override fun navigateToWorkScreen(trainingEntity: TrainingEntity) {
		router.navigateTo(getTimerWorkScreen(trainingEntity))
	}
}