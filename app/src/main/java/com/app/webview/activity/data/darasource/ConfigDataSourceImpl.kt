package com.app.webview.activity.data.darasource

import android.content.SharedPreferences
import com.app.webview.activity.domain.entity.ConfigEntity

class ConfigDataSourceImpl(private val sharedPreferences: SharedPreferences) : ConfigDataSource {

	private companion object {

		const val URL_KEY = "URL_KEY"
	}

	override fun saveConfig(config: ConfigEntity) {
		sharedPreferences.edit().apply {
			putString(URL_KEY, config.url)
		}.apply()
	}

	override fun getConfig(): ConfigEntity {
		val url = sharedPreferences.getString(URL_KEY, null) ?: ""
		return ConfigEntity(url)
	}
}