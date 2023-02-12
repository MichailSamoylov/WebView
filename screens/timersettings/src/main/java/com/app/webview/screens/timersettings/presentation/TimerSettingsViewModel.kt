package com.app.webview.screens.timersettings.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.webview.components.stub.timer.domain.TimerData
import com.app.webview.components.stub.trainings.doamin.entity.TrainingEntity
import com.app.webview.components.stub.trainings.doamin.usecase.GetUniqueNamePrefixUseCase
import com.app.webview.components.timeconverter.HOUR
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

class TimerSettingsViewModel(
	private val router: TimerSettingsRouter,
	private val getUniqueNamePrefixUseCase: GetUniqueNamePrefixUseCase
) : ViewModel() {

	private val _state = MutableLiveData<TimerSettingsState>()
	val state: LiveData<TimerSettingsState> = _state

	init {
		_state.value = TimerSettingsState.Content(0L, 0L, 0L, 0)
	}

	private lateinit var trainingEntity: TrainingEntity
	private val dataFormat = SimpleDateFormat("mm:ss", Locale.ENGLISH)

	fun onSelectedTraining(trainingEntity: TrainingEntity?) {
		if (trainingEntity != null) {
			this.trainingEntity = trainingEntity
			with(trainingEntity) {
				_state.value = TimerSettingsState.Content(timerData.pred, timerData.work, timerData.rest, timerData.rounds)
			}
		}
	}

	fun setPredPeriod(pred: String) {
		val currentState = _state.value as TimerSettingsState.Content
		if (pred.length >= 5) {
			tryParseTime(pred)?.let {
				val time = getHourOrTime(it)
				if (currentState.pred != time)
					_state.value = currentState.copy(pred = time)
			}
		}
	}

	fun setWorkPeriod(work: String) {
		val currentState = _state.value as TimerSettingsState.Content
		if (work.length >= 5) {
			tryParseTime(work)?.let {
				val time = getHourOrTime(it)
				if (currentState.work != time)
					_state.value = currentState.copy(work = time)
			}
		}
	}

	fun setRestPeriod(rest: String) {
		val currentState = _state.value as TimerSettingsState.Content
		if (rest.length >= 5) {
			tryParseTime(rest)?.let {
				val time = getHourOrTime(it)
				if (currentState.rest != time)
					_state.value = currentState.copy(rest = time)
			}
		}
	}

	private fun getHourOrTime(time: Long): Long {
		return if (time > HOUR) {
			HOUR
		} else {
			time
		}
	}

	private fun tryParseTime(time: String): Long? {
		var result: Long? = null
		try {
			val parseResult = dataFormat.parse(time)?.time ?: 0
			result = if (parseResult < 0L) {
				parseResult + 25200000L
			} else {
				parseResult
			}
		} catch (_: java.lang.Exception) {
		}
		return result
	}

	fun setRoundsCount(roundsCount: String) {
		val currentState = _state.value as TimerSettingsState.Content
		try {
			if (currentState.rounds != roundsCount.toInt())
				_state.value = currentState.copy(rounds = roundsCount.toInt())
		} catch (_: Exception) {
		}
	}

	fun startWorking() {
		val currentState = _state.value as TimerSettingsState.Content
		val workTime = (currentState.pred + currentState.work + currentState.rest) * currentState.rounds
		if (workTime.absoluteValue > 0) {
			if (::trainingEntity.isInitialized) {
				val newTrainingEntity = trainingEntity.copy(
					timerData = TimerData(
						pred = currentState.pred,
						work = currentState.work,
						rest = currentState.rest,
						rounds = currentState.rounds
					)
				)
				router.navigateToWorkScreen(newTrainingEntity)
			} else {
				val newTrainingEntity = TrainingEntity(
					name = "preset +${getUniqueNamePrefixUseCase()}",
					timerData = TimerData(
						pred = currentState.pred,
						work = currentState.work,
						rest = currentState.rest,
						rounds = currentState.rounds
					),
					setOf()
				)
				router.navigateToWorkScreen(newTrainingEntity)
			}
		}
	}

	fun navigateToTrainingsScreen() {
		router.navigateToTrainingsScreen()
	}
}