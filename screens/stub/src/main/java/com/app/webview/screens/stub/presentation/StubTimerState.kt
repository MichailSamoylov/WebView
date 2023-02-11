package com.app.webview.screens.stub.presentation

sealed class StubTimerState {

	data class Content(
		val timerMode: TimerMode
	) : StubTimerState()
}

sealed class TimerMode {

	data class Settings(
		val pred: Long,
		val work: Long,
		val rest: Long,
		val rounds: Int,
	) : TimerMode()

	data class Working(
		val workingTime: Long
	) : TimerMode()
}
