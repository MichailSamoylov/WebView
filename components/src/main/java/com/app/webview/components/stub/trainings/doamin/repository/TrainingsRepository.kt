package com.app.webview.components.stub.trainings.doamin.repository

import com.app.webview.components.stub.trainings.doamin.entity.TrainingEntity

interface TrainingsRepository {

	fun getTrainingsList(): List<TrainingEntity>

	fun saveTrainings(trainingEntity: TrainingEntity)

	fun getUniqueNamePrefix():String
}