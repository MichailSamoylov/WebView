package com.app.webview.components.stub.trainings.doamin.entity

import com.app.webview.components.stub.timer.domain.TimerData

data class TrainingEntity(
	val name: String,
	val timerData: TimerData,
	val listOfUsing: Set<String>
):java.io.Serializable
