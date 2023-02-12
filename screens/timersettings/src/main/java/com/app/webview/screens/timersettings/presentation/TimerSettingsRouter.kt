package com.app.webview.screens.timersettings.presentation

import com.app.webview.components.stub.domain.TimerData

interface TimerSettingsRouter {

	fun navigateToWorkScreen(workTime: TimerData)
}