package com.app.webview.screens.stub.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class StubTimerViewModel : ViewModel() {

	private val _state = MutableLiveData<StubTimerState>()
	val state: LiveData<StubTimerState> = _state

	companion object{
		const val HOUR = 3599000L
		const val DAY = 86399000L
	}

	init {
		_state.value = StubTimerState.Content(TimerMode.Settings(0L, 0L, 0L, 0))
	}

	private val dataFormat = SimpleDateFormat("mm:ss", Locale("ru"))

	fun setPredPeriod(pred: String) {
		val currentState = ((_state.value as StubTimerState.Content).timerMode as? TimerMode.Settings) ?: return
		if (pred.length >= 5) {
			tryParseTime(pred)?.let {
				val time = getLongTime(it)
				if (currentState.pred != time)
					_state.value = StubTimerState.Content(currentState.copy(pred = time))
			}
		}
	}

	fun setWorkPeriod(work: String) {
		val currentState = ((_state.value as StubTimerState.Content).timerMode as? TimerMode.Settings) ?: return
		if (work.length >= 5) {
			tryParseTime(work)?.let {
				val time = getLongTime(it)
				if (currentState.work != time)
					_state.value = StubTimerState.Content(currentState.copy(work = time))
			}
		}
	}

	fun setRestPeriod(rest: String) {
		val currentState = ((_state.value as StubTimerState.Content).timerMode as? TimerMode.Settings) ?: return
		if (rest.length >= 5) {
			tryParseTime(rest)?.let {
				val time = getLongTime(it)
				if (currentState.rest != time)
					_state.value = StubTimerState.Content(currentState.copy(rest = time))
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
		val currentState = ((_state.value as StubTimerState.Content).timerMode as? TimerMode.Settings) ?: return
		try {
			if (currentState.rounds != roundsCount.toInt())
				_state.value = StubTimerState.Content(currentState.copy(rounds = roundsCount.toInt()))
		} catch (_: Exception) { }
	}

	fun startWorking() {
		val currentState = ((_state.value as StubTimerState.Content).timerMode as? TimerMode.Settings) ?: return
		with(currentState) {
			val resultTime = (pred + work + rest) * rounds
			_state.value = StubTimerState.Content(TimerMode.Working(resultTime))
		}
	}
}