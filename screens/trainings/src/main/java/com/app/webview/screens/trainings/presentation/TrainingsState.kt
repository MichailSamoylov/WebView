package com.app.webview.screens.trainings.presentation

import com.app.webview.components.stub.trainings.doamin.entity.TrainingEntity

sealed class TrainingsState {
	data class Content(
		val trainings: List<TrainingEntity>
	) : TrainingsState()

	data class Exit(
		val training: TrainingEntity
	) : TrainingsState()
}
