package com.app.webview.activity.presentation

import android.net.ConnectivityManager
import android.os.Build
import android.telephony.TelephonyManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.webview.activity.domain.entity.ConfigEntity
import com.app.webview.activity.domain.usecase.GetConfigUseCase
import com.app.webview.activity.domain.usecase.SaveConfigUseCase
import com.app.webview.screens.error.BuildConfig
import com.app.webview.screens.error.getErrorScreen
import com.app.webview.screens.stub.getStubTimerScreen
import com.app.webview.screens.webview.getWebViewScreen
import com.github.terrakok.cicerone.Router
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Locale
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel(
	private val router: Router,
	private val getConfigUseCase: GetConfigUseCase,
	private val saveConfigUseCase: SaveConfigUseCase,
	private val remoteConfig: FirebaseRemoteConfig,
	private val connectivityManager: ConnectivityManager,
	private val telephonyManager: TelephonyManager,
) : ViewModel() {

	private companion object {

		const val JSON_CONFIG_KEY = "url"
	}

	private val gson = Gson()

	fun checkConfig() {
		if (getConfigUseCase().url.isEmpty()) {
			reloadConfig()
		} else {
			if (checkInternetConnection()) {
				router.newRootScreen(getWebViewScreen())
			} else {
				router.newRootScreen(getErrorScreen())
			}
		}
	}

	private fun checkInternetConnection(): Boolean =
		connectivityManager.activeNetwork != null

	private fun reloadConfig() {
		viewModelScope.launch {
			remoteConfig.fetchAndActivate()
			delay(2000)
			val jsonConfig = remoteConfig.getString(JSON_CONFIG_KEY)
			val config = gson.fromJson(jsonConfig, object : TypeToken<ConfigEntity>() {}.type) as? ConfigEntity
			if (config != null)
				onNotEmptyConfig(config)
			else
				router.newRootScreen(getStubTimerScreen())
		}
	}

	private fun onNotEmptyConfig(config: ConfigEntity) {
		saveConfigUseCase(config)

		if (config.url.isEmpty() || deviceIsEmulator() || deviceDontHaveSIMCard()) {
			router.newRootScreen(getStubTimerScreen())
		} else {
			router.newRootScreen(getWebViewScreen())
		}
	}

	private fun deviceIsEmulator(): Boolean {
		if (BuildConfig.DEBUG) return false
		val phoneModel = Build.MODEL
		val buildProduct = Build.PRODUCT
		val buildHardware = Build.HARDWARE

		var result = (Build.FINGERPRINT.startsWith("generic")
			|| phoneModel.contains("google_sdk")
			|| phoneModel.lowercase(Locale.getDefault()).contains("droid4x")
			|| phoneModel.contains("Emulator")
			|| phoneModel.contains("Android SDK built for x86")
			|| Build.MANUFACTURER.contains("Genymotion")
			|| buildHardware == "goldfish"
			|| Build.BRAND.contains("google")
			|| buildHardware == "vbox86"
			|| buildProduct == "sdk"
			|| buildProduct == "google_sdk"
			|| buildProduct == "sdk_x86"
			|| buildProduct == "vbox86p"
			|| Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
			|| Build.BOOTLOADER.lowercase(Locale.getDefault()).contains("nox")
			|| buildHardware.lowercase(Locale.getDefault()).contains("nox")
			|| buildProduct.lowercase(Locale.getDefault()).contains("nox"))
		if (result) return true
		result = result or (Build.BRAND.startsWith("generic") &&
			Build.DEVICE.startsWith("generic"))
		if (result) return true
		result = result or ("google_sdk" == buildProduct)
		return result

	}

	private fun deviceDontHaveSIMCard(): Boolean {
		return telephonyManager.simState == TelephonyManager.SIM_STATE_ABSENT
	}
}