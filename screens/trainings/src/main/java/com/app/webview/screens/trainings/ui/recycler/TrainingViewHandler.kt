package com.app.webview.screens.trainings.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.webview.screens.trainings.databinding.TrainingItemBinding
import com.app.webview.components.stub.trainings.doamin.entity.TrainingEntity

class TrainingViewHandler(
	private val binding: TrainingItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

	companion object {

		fun from(parent: ViewGroup): TrainingViewHandler {
			val inflater = LayoutInflater.from(parent.context)
			val binding = TrainingItemBinding.inflate(inflater, parent, false)
			return TrainingViewHandler(binding)
		}
	}

	fun bind(
		trainingEntity: TrainingEntity,
		onClick: (TrainingEntity) -> Unit
	) {
		with(binding) {
			name.text = trainingEntity.name
			datesText.text = buildString {
				trainingEntity.listOfUsing.forEach {
					append("$it,  ")
				}
			}
			listItem.setOnClickListener {
				onClick(trainingEntity)
			}
		}
	}
}