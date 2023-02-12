package com.app.webview.components.stub.trainings.data.datasource

import com.app.webview.components.stub.trainings.doamin.entity.TrainingEntity

interface TrainingDataSource {

	fun saveTraining(trainingEntity: TrainingEntity)

	fun getListTraining(): List<TrainingEntity>

	fun getUniqueNamePrefix(): String
}