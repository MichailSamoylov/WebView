package com.app.webview.screens.timersettings.presentation

sealed class TimerSettingsState {

	data class Content(
		val pred: Long,
		val work: Long,
		val rest: Long,
		val rounds: Int,
	) : TimerSettingsState()
}
