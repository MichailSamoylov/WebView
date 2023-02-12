package com.app.webview.activity.presentation

import android.net.ConnectivityManager
import android.os.Build
import android.telephony.TelephonyManager
import androidx.lifecycle.ViewModel
import com.app.webview.activity.domain.entity.ConfigEntity
import com.app.webview.activity.domain.usecase.GetConfigUseCase
import com.app.webview.activity.domain.usecase.SaveConfigUseCase
import com.app.webview.screens.error.getErrorScreen
import com.app.webview.screens.timersettings.getStubTimerScreen
import com.app.webview.screens.webview.getWebViewScreen
import com.github.terrakok.cicerone.Router
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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
		if (checkInternetConnection()) {
			val config = getConfigUseCase()
			remoteConfig.reset()
			if (config.url.isEmpty()) {
				reloadConfigAndNavigate()
			} else {
				reloadConfig()
				router.newRootScreen(getWebViewScreen(config.url))
			}
		} else {
			router.newRootScreen(getErrorScreen())
		}
	}

	private fun checkInternetConnection(): Boolean =
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			connectivityManager.activeNetwork != null
		} else {
			connectivityManager.activeNetworkInfo != null
		}

	private fun reloadConfig() {
		remoteConfig.fetchAndActivate().addOnCompleteListener {
			val jsonConfig = remoteConfig.getString(JSON_CONFIG_KEY)
			val config = gson.fromJson(jsonConfig, object : TypeToken<ConfigEntity>() {}.type) as? ConfigEntity
			if (config != null) {
				saveConfigUseCase(config)
			}
		}
	}

	private fun reloadConfigAndNavigate() {
		remoteConfig.fetchAndActivate().addOnCompleteListener {
			val jsonConfig = remoteConfig.getString(JSON_CONFIG_KEY)
			val config = gson.fromJson(jsonConfig, object : TypeToken<ConfigEntity>() {}.type) as? ConfigEntity
			if (config != null) {
				saveConfigUseCase(config)
			}

			if (config != null)
				onNotEmptyConfig(config)
			else
				router.newRootScreen(getStubTimerScreen())
		}
	}

	private fun onNotEmptyConfig(config: ConfigEntity) {
		if (config.url.isEmpty() || deviceIsEmulator() || deviceIsGoogleProduction() || deviceWithoutSIMCard()) {
			router.newRootScreen(getStubTimerScreen())
		} else {
			router.newRootScreen(getWebViewScreen(config.url))
		}
	}

	private fun deviceIsGoogleProduction(): Boolean {
		val phoneModel = Build.MODEL
		val buildProduct = Build.PRODUCT

		return phoneModel.contains("google_sdk")
			|| Build.BRAND.contains("google")
			|| buildProduct == "google_sdk"
	}

	private fun deviceIsEmulator(): Boolean {
		return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
			|| Build.FINGERPRINT.startsWith("generic")
			|| Build.FINGERPRINT.startsWith("unknown")
			|| Build.HARDWARE.contains("goldfish")
			|| Build.MODEL.contains("google_sdk")
			|| Build.MODEL.contains("Emulator")
			|| Build.MODEL.contains("Android SDK built for x86")
			|| Build.MANUFACTURER.contains("Genymotion")
			|| Build.PRODUCT.contains("sdk_google")
			|| Build.PRODUCT.contains("google_sdk")
			|| Build.PRODUCT.contains("sdk")
			|| Build.PRODUCT.contains("sdk_x86")
			|| Build.PRODUCT.contains("sdk_gphone64_arm64")
			|| Build.PRODUCT.contains("vbox86p")
			|| Build.PRODUCT.contains("emulator")
			|| Build.PRODUCT.contains("simulator");
	}

	private fun deviceWithoutSIMCard(): Boolean {
		return telephonyManager.simState == TelephonyManager.SIM_STATE_ABSENT
	}
}