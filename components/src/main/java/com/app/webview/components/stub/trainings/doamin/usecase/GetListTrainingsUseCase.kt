package com.app.webview.components.stub.trainings.doamin.usecase

import com.app.webview.components.stub.trainings.doamin.entity.TrainingEntity
import com.app.webview.components.stub.trainings.doamin.repository.TrainingsRepository

class GetListTrainingsUseCase(private val repository: TrainingsRepository) {

	operator fun invoke(): List<TrainingEntity> = repository.getTrainingsList()
}