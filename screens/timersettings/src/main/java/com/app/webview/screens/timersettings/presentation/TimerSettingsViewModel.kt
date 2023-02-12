package com.app.webview.screens.timersettings.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.webview.components.stub.domain.TimerData
import com.app.webview.components.timeconverter.HOUR
import java.text.SimpleDateFormat
import java.util.Locale

class TimerSettingsViewModel(
	private val router: TimerSettingsRouter
) : ViewModel() {

	private val _state = MutableLiveData<TimerSettingsState>()
	val state: LiveData<TimerSettingsState> = _state

	init {
		_state.value = TimerSettingsState.Content(0L, 0L, 0L, 0)
	}

	private val dataFormat = SimpleDateFormat("mm:ss", Locale("ru"))

	fun setPredPeriod(pred: String) {
		val currentState = _state.value as TimerSettingsState.Content
		if (pred.length >= 5) {
			tryParseTime(pred)?.let {
				val time = getLongTime(it)
				if (currentState.pred != time)
					_state.value = currentState.copy(pred = time)
			}
		}
	}

	fun setWorkPeriod(work: String) {
		val currentState = _state.value as TimerSettingsState.Content
		if (work.length >= 5) {
			tryParseTime(work)?.let {
				val time = getLongTime(it)
				if (currentState.work != time)
					_state.value = currentState.copy(work = time)
			}
		}
	}

	fun setRestPeriod(rest: String) {
		val currentState = _state.value as TimerSettingsState.Content
		if (rest.length >= 5) {
			tryParseTime(rest)?.let {
				val time = getLongTime(it)
				if (currentState.rest != time)
					_state.value = currentState.copy(rest = time)
			}
		}
	}

	private fun getLongTime(time: Long): Long {
		return if (time > HOUR) {
			HOUR
		} else {
			time
		}
	}

	private fun tryParseTime(time: String): Long? {
		var result: Long? = null
		try {
			result = dataFormat.parse(time)?.time
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
		if (workTime > 0)
			router.navigateToWorkScreen(
				TimerData(
					pred = currentState.pred,
					work = currentState.work,
					rest = currentState.rest,
					rounds = currentState.rounds
				)
			)
	}
}