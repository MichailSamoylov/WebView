package com.app.webview.components.stub.trainings.data.repository

import com.app.webview.components.stub.trainings.data.datasource.TrainingDataSource
import com.app.webview.components.stub.trainings.doamin.entity.TrainingEntity
import com.app.webview.components.stub.trainings.doamin.repository.TrainingsRepository

class TrainingsRepositoryImpl(private val dataSource: TrainingDataSource) : TrainingsRepository {

	override fun getTrainingsList(): List<TrainingEntity> = dataSource.getListTraining()

	override fun saveTrainings(trainingEntity: TrainingEntity) {
		dataSource.saveTraining(trainingEntity)
	}

	override fun getUniqueNamePrefix(): String = dataSource.getUniqueNamePrefix()
}