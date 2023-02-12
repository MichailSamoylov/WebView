package com.app.webview.screens.trainings.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.webview.components.stub.trainings.doamin.entity.TrainingEntity
import com.app.webview.components.stub.trainings.doamin.usecase.GetListTrainingsUseCase

class TrainingViewModel(
	private val router: TrainingRouter,
	private val getListTrainingsUseCase: GetListTrainingsUseCase,
) : ViewModel() {

	private val _state = MutableLiveData<TrainingsState>()
	val state: LiveData<TrainingsState> = _state

	init {
		_state.value = TrainingsState.Content(getListTrainingsUseCase())
	}

	fun clickByItem(trainingEntity: TrainingEntity) {
		_state.value = TrainingsState.Exit(trainingEntity)
	}

	fun navigateBack() {
		router.navigateBack()
	}
}