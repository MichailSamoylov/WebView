package com.app.webview.screens.trainings.di

import android.content.Context
import com.app.webview.components.stub.trainings.data.datasource.TrainingDataSource
import com.app.webview.components.stub.trainings.data.datasource.TrainingDataSourceImpl
import com.app.webview.components.stub.trainings.data.repository.TrainingsRepositoryImpl
import com.app.webview.components.stub.trainings.doamin.repository.TrainingsRepository
import com.app.webview.components.stub.trainings.doamin.usecase.GetListTrainingsUseCase
import com.app.webview.components.stub.trainings.doamin.usecase.GetUniqueNamePrefixUseCase
import com.app.webview.components.stub.trainings.doamin.usecase.SaveTrainingUseCase
import com.app.webview.screens.trainings.presentation.TrainingViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val trainingsModule = module {
	factory<TrainingDataSource> {
		TrainingDataSourceImpl(
			androidContext().getSharedPreferences(
				"CONFIG",
				Context.MODE_PRIVATE
			)
		)
	}
	factory<TrainingsRepository> { TrainingsRepositoryImpl(get()) }
	factory { SaveTrainingUseCase(get()) }
	factory { GetUniqueNamePrefixUseCase(get()) }
	factory { GetListTrainingsUseCase(get()) }
	viewModel {
		TrainingViewModel(
			router = get(),
			getListTrainingsUseCase = get()
		)
	}
}