package com.app.webview.screens.trainings.ui.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.app.webview.components.stub.trainings.doamin.entity.TrainingEntity

class TrainingAdapter(
	private val onItemClick: (TrainingEntity) -> Unit
) : ListAdapter<TrainingEntity, TrainingViewHandler>(TrainDiffCallback) {

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): TrainingViewHandler =
		TrainingViewHandler.from(parent)

	override fun onBindViewHolder(holderArchive: TrainingViewHandler, position: Int) {
		holderArchive.bind(getItem(position), onItemClick)
	}
}

object TrainDiffCallback : DiffUtil.ItemCallback<TrainingEntity>() {

	override fun areItemsTheSame(
		oldItem: TrainingEntity,
		newItem: TrainingEntity
	): Boolean = oldItem == newItem

	override fun areContentsTheSame(
		oldItem: TrainingEntity,
		newItem: TrainingEntity
	): Boolean = oldItem.timerData == newItem.timerData
}