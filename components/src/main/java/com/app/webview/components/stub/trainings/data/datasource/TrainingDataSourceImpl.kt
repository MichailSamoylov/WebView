package com.app.webview.components.stub.trainings.data.datasource

import android.content.SharedPreferences
import com.app.webview.components.stub.timer.domain.TimerData
import com.app.webview.components.stub.trainings.doamin.entity.TrainingEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TrainingDataSourceImpl(private val sharedPreferences: SharedPreferences) : TrainingDataSource {

	private companion object {

		const val TRAININGS_NAMES_KEY = "TRAININGS_NAMES_KEY"
	}

	private val gson = Gson()
	private val trainingsNames: MutableSet<String> = getTrainingsNames()

	private fun getTrainingsNames(): MutableSet<String> {
		val jsonList = sharedPreferences.getString(TRAININGS_NAMES_KEY, "")
		return gson.fromJson(jsonList, object : TypeToken<MutableSet<String>>() {}.type) as? MutableSet<String> ?: mutableSetOf()
	}

	private fun saveTrainingsNames() {
		val jsonList = gson.toJson(trainingsNames)
		sharedPreferences.edit().apply { putString(TRAININGS_NAMES_KEY, jsonList) }.apply()
	}

	override fun saveTraining(trainingEntity: TrainingEntity) {
		trainingsNames.add(trainingEntity.name)
		val jsonEntity = gson.toJson(trainingEntity)
		sharedPreferences.edit().apply { putString(trainingsNames.last(), jsonEntity) }.apply()
		saveTrainingsNames()
	}

	override fun getListTraining(): List<TrainingEntity> {
		val listOfTrainings = mutableListOf<TrainingEntity>()
		trainingsNames.forEach { name ->
			listOfTrainings.add(getTrainingByName(name))
		}
		return listOfTrainings
	}

	override fun getUniqueNamePrefix(): String {
		return trainingsNames.size.toString()
	}

	private fun getTrainingByName(name: String): TrainingEntity {
		val jsonEntity = sharedPreferences.getString(name, "")
		return gson.fromJson(jsonEntity, object : TypeToken<TrainingEntity>() {}.type) as? TrainingEntity ?: TrainingEntity(
			name,
			TimerData(0, 0, 0, 0),
			setOf()
		)
	}
}