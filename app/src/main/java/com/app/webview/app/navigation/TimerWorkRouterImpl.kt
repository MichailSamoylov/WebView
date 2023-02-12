package com.app.webview.app.navigation

import com.app.webview.screens.timerwork.presentation.TimerWorkRouter
import com.github.terrakok.cicerone.Router

class TimerWorkRouterImpl(private val route: Router):TimerWorkRouter {

	override fun navigateBack() {
		route.exit()
	}
}