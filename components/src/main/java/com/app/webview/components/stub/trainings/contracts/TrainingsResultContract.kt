package com.app.webview.components.stub.trainings.contracts

import android.os.Bundle
import androidx.core.os.bundleOf
import com.app.webview.components.fragment.FragmentResultContract
import com.app.webview.components.stub.trainings.doamin.entity.TrainingEntity

object TrainingsResultContract : FragmentResultContract<TrainingEntity> {

	override val requestKey: String = "INVENTORY_REQUEST_KEY"

	override fun getResult(bundle: Bundle): TrainingEntity =
		bundle.getSerializable(requestKey) as TrainingEntity

	override fun setResult(result: TrainingEntity): Bundle =
		bundleOf(requestKey to result)
}