package com.app.webview.activity.domain.usecase

import com.app.webview.activity.domain.entity.ConfigEntity
import com.app.webview.activity.domain.repository.ConfigRepository

class GetConfigUseCase(private val repository: ConfigRepository) {

	operator fun invoke(): ConfigEntity = repository.getConfig()
}