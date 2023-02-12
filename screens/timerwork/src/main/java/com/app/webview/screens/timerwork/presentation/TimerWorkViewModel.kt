package com.app.webview.screens.timerwork.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.webview.components.stub.domain.TimerData
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerWorkViewModel(
	private val workTime: TimerData,
	private val router: TimerWorkRouter,
) : ViewModel() {

	private val _state = MutableLiveData<TimerWorkState>()
	val state: LiveData<TimerWorkState> = _state

	companion object {

		const val SECOND = 1000L
	}

	init {
		_state.value = TimerWorkState.Content(timerState = TimerState.Resume, workTime = workTime, TimeTypeDisplayed.Pred(workTime.pred))
		startTimer()
	}

	fun setTimerStatePauseOrResume() {
		val currentState = _state.value as TimerWorkState.Content
		when (currentState.timerState) {
			TimerState.Pause  -> {
				_state.value = currentState.copy(timerState = TimerState.Resume)
				startTimer()
			}

			TimerState.Resume -> {
				_state.value = currentState.copy(timerState = TimerState.Pause)
			}

			TimerState.Stop   -> Unit
		}
	}

	private fun startTimer() {
		viewModelScope.launch {
			var currentState = _state.value as TimerWorkState.Content

			while (currentState.timerState == TimerState.Resume) {
				updateState(currentState)
				currentState = _state.value as TimerWorkState.Content
			}
		}
	}

	private suspend fun updateState(state: TimerWorkState.Content) {
		when (state.timeTypeDisplayed) {
			is TimeTypeDisplayed.Pred -> {
				updatePredTime(state, state.timeTypeDisplayed)
			}

			is TimeTypeDisplayed.Rest -> {
				updateRestTime(state, state.timeTypeDisplayed)
			}

			is TimeTypeDisplayed.Work -> {
				updateWorkTime(state, state.timeTypeDisplayed)
			}
		}
	}

	private suspend fun updatePredTime(state: TimerWorkState.Content, timeTypeDisplayed: TimeTypeDisplayed.Pred) {
		if (timeTypeDisplayed.time != 0L) {
			_state.value = state.copy(timeTypeDisplayed = TimeTypeDisplayed.Pred(timeTypeDisplayed.time - SECOND))
			delay(SECOND)
		} else {
			_state.value = state.copy(timeTypeDisplayed = TimeTypeDisplayed.Work(state.workTime.work))
		}
	}

	private suspend fun updateWorkTime(state: TimerWorkState.Content, timeTypeDisplayed: TimeTypeDisplayed.Work) {
		if (timeTypeDisplayed.time != 0L) {
			_state.value = state.copy(timeTypeDisplayed = TimeTypeDisplayed.Work(timeTypeDisplayed.time - SECOND))
			delay(SECOND)
		} else {
			_state.value = state.copy(timeTypeDisplayed = TimeTypeDisplayed.Rest(state.workTime.rest))
		}
	}

	private suspend fun updateRestTime(state: TimerWorkState.Content, timeTypeDisplayed: TimeTypeDisplayed.Rest) {
		if (timeTypeDisplayed.time != 0L) {
			_state.value = state.copy(timeTypeDisplayed = TimeTypeDisplayed.Rest(timeTypeDisplayed.time - SECOND))
			delay(SECOND)
		} else {
			val newCountRounds = state.workTime.rounds - 1
			val newTimerData = TimerData(
				pred = state.workTime.pred,
				work = state.workTime.work,
				rest = state.workTime.rest,
				rounds = newCountRounds
			)
			if (newCountRounds != 0) {
				_state.value = state.copy(timeTypeDisplayed = TimeTypeDisplayed.Work(state.workTime.work), workTime = newTimerData)
			} else {
				_state.value = state.copy(timerState = TimerState.Stop)
				setTimerStateStop()
			}
		}
	}

	fun setTimerStateStop() {
		router.navigateBack()
	}
}