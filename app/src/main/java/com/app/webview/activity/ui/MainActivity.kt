package com.app.webview.activity.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.webview.R
import com.app.webview.activity.presentation.MainActivityViewModel
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

	private val navigateHolder: NavigatorHolder by inject()
	private val navigator = AppNavigator(this, R.id.container)
	private val remoteConfig = FirebaseRemoteConfig.getInstance()
	private val viewModel: MainActivityViewModel by viewModel { parametersOf(remoteConfig) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		viewModel.checkConfig()
	}

	override fun onResume() {
		super.onResume()
		navigateHolder.setNavigator(navigator)
	}

	override fun onPause() {
		super.onPause()
		navigateHolder.removeNavigator()
	}
}