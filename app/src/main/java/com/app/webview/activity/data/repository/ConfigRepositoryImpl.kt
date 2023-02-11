package com.app.webview.activity.data.repository

import com.app.webview.activity.data.darasource.ConfigDataSource
import com.app.webview.activity.domain.entity.ConfigEntity
import com.app.webview.activity.domain.repository.ConfigRepository

class ConfigRepositoryImpl(private val dataSource: ConfigDataSource): ConfigRepository {

	override fun saveConfig(config: ConfigEntity) {
		dataSource.saveConfig(config)
	}

	override fun getConfig(): ConfigEntity = dataSource.getConfig()
}