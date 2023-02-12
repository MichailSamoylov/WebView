package com.app.webview.components.stub.timer.domain

data class TimerData(
	val pred: Long,
	val work: Long,
	val rest: Long,
	val rounds: Int,
) : java.io.Serializable
