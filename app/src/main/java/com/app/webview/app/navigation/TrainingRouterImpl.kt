package com.app.webview.app.navigation

import com.app.webview.screens.trainings.presentation.TrainingRouter
import com.github.terrakok.cicerone.Router

class TrainingRouterImpl(private val router: Router) : TrainingRouter {

	override fun navigateBack() {
		router.exit()
	}
}