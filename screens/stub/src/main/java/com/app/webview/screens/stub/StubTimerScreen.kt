package com.app.webview.screens.stub

import com.app.webview.screens.stub.ui.StubTimerFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getStubTimerScreen() = FragmentScreen { StubTimerFragment.newInstance() }