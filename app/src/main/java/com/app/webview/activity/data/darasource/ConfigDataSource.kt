package com.app.webview.activity.data.darasource

import com.app.webview.activity.domain.entity.ConfigEntity

interface ConfigDataSource {

	fun saveConfig(config: ConfigEntity)

	fun getConfig(): ConfigEntity
}