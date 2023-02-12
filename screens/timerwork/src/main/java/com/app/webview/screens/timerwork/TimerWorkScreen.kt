package com.app.webview.screens.timerwork

import com.app.webview.components.stub.trainings.doamin.entity.TrainingEntity
import com.app.webview.screens.timerwork.ui.TimerWorkFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getTimerWorkScreen(trainingDate: TrainingEntity) = FragmentScreen { TimerWorkFragment.newInstance(trainingDate) }