package com.app.webview.screens.timerwork.presentation

import com.app.webview.components.stub.domain.TimerData

sealed class TimerWorkState {

	data class Content(
		val timerState: TimerState,
		val workTime: TimerData,
		val timeTypeDisplayed: TimeTypeDisplayed
	) : TimerWorkState()
}

sealed class TimeTypeDisplayed {

	data class Work(
		val time: Long
	) : TimeTypeDisplayed()

	data class Rest(
		val time: Long
	) : TimeTypeDisplayed()

	data class Pred(
		val time: Long
	) : TimeTypeDisplayed()
}

sealed class TimerState {

	object Resume : TimerState()

	object Pause : TimerState()

	object Stop : TimerState()
}
