package com.app.webview.screens.error

import com.app.webview.screens.error.ui.ErrorFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getErrorScreen() = FragmentScreen { ErrorFragment.newInstance() }