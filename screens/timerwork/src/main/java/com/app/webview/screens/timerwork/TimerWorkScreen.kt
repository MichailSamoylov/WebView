package com.app.webview.screens.timerwork

import com.app.webview.components.stub.domain.TimerData
import com.app.webview.screens.timerwork.ui.TimerWorkFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getTimerWorkScreen(workTime: TimerData) = FragmentScreen { TimerWorkFragment.newInstance(workTime) }