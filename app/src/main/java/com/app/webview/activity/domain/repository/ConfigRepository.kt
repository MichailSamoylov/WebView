package com.app.webview.activity.domain.repository

import com.app.webview.activity.domain.entity.ConfigEntity

interface ConfigRepository {

	fun saveConfig(config: ConfigEntity)

	fun getConfig(): ConfigEntity
}