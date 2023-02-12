package com.app.webview.screens.trainings.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.webview.components.fragment.setResult
import com.app.webview.components.stub.trainings.contracts.TrainingsResultContract
import com.app.webview.screens.trainings.R
import com.app.webview.screens.trainings.databinding.FragmentTrainingsBinding
import com.app.webview.screens.trainings.presentation.TrainingViewModel
import com.app.webview.screens.trainings.presentation.TrainingsState
import com.app.webview.screens.trainings.ui.recycler.TrainingAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrainingsFragment : Fragment(R.layout.fragment_trainings) {

	companion object {

		fun newInstance() = TrainingsFragment()
	}

	private val adapter: TrainingAdapter by lazy { TrainingAdapter(viewModel::clickByItem) }
	private lateinit var binding: FragmentTrainingsBinding
	private val viewModel: TrainingViewModel by viewModel()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentTrainingsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.trainings.adapter = adapter
		setObservers()
	}

	private fun setObservers() {
		viewModel.state.observe(viewLifecycleOwner, ::handleState)
	}

	private fun handleState(state: TrainingsState) {
		when (state) {
			is TrainingsState.Content -> renderContent(state)
			is TrainingsState.Exit    -> doOnExit(state)
		}
	}

	@SuppressLint("NotifyDataSetChanged")
	private fun renderContent(state: TrainingsState.Content) {
		with(binding) {
			adapter.submitList(state.trainings)
		}
	}

	private fun doOnExit(state: TrainingsState.Exit) {
		setResult(TrainingsResultContract, state.training)
		viewModel.navigateBack()
	}
}