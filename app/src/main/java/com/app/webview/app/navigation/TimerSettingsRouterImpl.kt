package com.app.webview.app.navigation

import com.app.webview.components.stub.domain.TimerData
import com.app.webview.screens.timersettings.presentation.TimerSettingsRouter
import com.app.webview.screens.timerwork.getTimerWorkScreen
import com.github.terrakok.cicerone.Router

class TimerSettingsRouterImpl(private val router: Router) : TimerSettingsRouter {

	override fun navigateToWorkScreen(workTime: TimerData) {
		router.navigateTo(getTimerWorkScreen(workTime))
	}
}