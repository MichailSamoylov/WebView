package com.app.webview.screens.webview

import com.app.webview.screens.webview.ui.WebViewFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getWebViewScreen(webUrl:String) = FragmentScreen { WebViewFragment.newInstance(webUrl) }