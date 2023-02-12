package com.app.webview.screens.timersettings

import com.app.webview.screens.timersettings.ui.TimerSettingsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getStubTimerScreen() = FragmentScreen { TimerSettingsFragment.newInstance() }