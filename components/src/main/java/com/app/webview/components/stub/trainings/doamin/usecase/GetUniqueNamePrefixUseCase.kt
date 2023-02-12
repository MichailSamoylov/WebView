package com.app.webview.components.stub.trainings.doamin.usecase

import com.app.webview.components.stub.trainings.doamin.repository.TrainingsRepository

class GetUniqueNamePrefixUseCase(private val repository: TrainingsRepository) {

	operator fun invoke(): String = repository.getUniqueNamePrefix()
}