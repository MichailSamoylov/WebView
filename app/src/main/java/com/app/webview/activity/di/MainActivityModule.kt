package com.app.webview.activity.di

import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import com.app.webview.activity.data.darasource.ConfigDataSource
import com.app.webview.activity.data.darasource.ConfigDataSourceImpl
import com.app.webview.activity.data.repository.ConfigRepositoryImpl
import com.app.webview.activity.domain.repository.ConfigRepository
import com.app.webview.activity.domain.usecase.GetConfigUseCase
import com.app.webview.activity.domain.usecase.SaveConfigUseCase
import com.app.webview.activity.presentation.MainActivityViewModel
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainActivityModule = module {
	factory<ConfigDataSource> {
		ConfigDataSourceImpl(
			androidContext().getSharedPreferences(
				"CONFIG",
				Context.MODE_PRIVATE
			)
		)
	}
	factory<ConfigRepository> { ConfigRepositoryImpl(get()) }
	factory { SaveConfigUseCase(get()) }
	factory { GetConfigUseCase(get()) }
	viewModel { (remoteConfig: FirebaseRemoteConfig) ->
		MainActivityViewModel(
			router = get(),
			getConfigUseCase = get(),
			saveConfigUseCase = get(),
			remoteConfig = remoteConfig,
			connectivityManager = androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager,
			telephonyManager = androidContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
		)
	}
}