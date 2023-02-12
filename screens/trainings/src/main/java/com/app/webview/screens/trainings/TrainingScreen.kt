package com.app.webview.screens.trainings

import com.app.webview.screens.trainings.ui.TrainingsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getTrainingsScreen() = FragmentScreen { TrainingsFragment.newInstance() }