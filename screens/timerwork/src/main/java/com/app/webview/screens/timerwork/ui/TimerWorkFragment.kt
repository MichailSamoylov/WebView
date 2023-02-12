package com.app.webview.screens.timerwork.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.webview.components.stub.trainings.doamin.entity.TrainingEntity
import com.app.webview.components.timeconverter.convertTimeHourToString
import com.app.webview.screens.timerwork.R
import com.app.webview.screens.timerwork.databinding.FragmentTimerWorkBinding
import com.app.webview.screens.timerwork.presentation.TimeTypeDisplayed
import com.app.webview.screens.timerwork.presentation.TimerState
import com.app.webview.screens.timerwork.presentation.TimerWorkState
import com.app.webview.screens.timerwork.presentation.TimerWorkViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val WORK_TIME = "WORK_TIME"

var Bundle.workTime
	get() = getSerializable(WORK_TIME)
	set(value) = putSerializable(WORK_TIME, value)

class TimerWorkFragment : Fragment(R.layout.fragment_timer_work) {

	companion object {

		fun newInstance(trainingDate: TrainingEntity) =
			TimerWorkFragment().apply {
				val bundle = Bundle()
				bundle.workTime = trainingDate
				arguments = bundle
			}
	}

	private lateinit var binding: FragmentTimerWorkBinding
	private val viewModel: TimerWorkViewModel by viewModel {
		parametersOf(arguments?.workTime)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentTimerWorkBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setObservers()
		setListeners()
	}

	private fun setListeners() {
		with(binding) {
			buttonPauseOrResume.setOnClickListener { viewModel.setTimerStatePauseOrResume() }
			buttonStop.setOnClickListener { viewModel.setTimerStateStop() }
		}
	}

	private fun setObservers() {
		viewModel.state.observe(viewLifecycleOwner, ::handleState)
	}

	private fun handleState(state: TimerWorkState) {
		when (state) {
			is TimerWorkState.Content -> renderContent(state)
		}
	}

	@SuppressLint("SetTextI18n")
	private fun renderContent(state: TimerWorkState.Content) {
		with(binding) {
			roundsText.text = (getString(R.string.rounds_text_prefix) + state.workTime.rounds)
			when (state.timerState) {
				TimerState.Resume -> {
					buttonPauseOrResume.text = resources.getString(R.string.button_pause_text)
				}

				TimerState.Pause  -> {
					buttonPauseOrResume.text = resources.getString(R.string.button_resume_text)
				}

				TimerState.Stop   -> Unit
			}
			when (state.timeTypeDisplayed) {
				is TimeTypeDisplayed.Pred -> {
					typeDisplayedTime.text = resources.getString(R.string.displayed_time_pred)
					timerText.text = convertTimeHourToString(state.timeTypeDisplayed.time)
				}

				is TimeTypeDisplayed.Rest -> {
					typeDisplayedTime.text = resources.getString(R.string.displayed_time_rest)
					timerText.text = convertTimeHourToString(state.timeTypeDisplayed.time)
				}

				is TimeTypeDisplayed.Work -> {
					typeDisplayedTime.text = resources.getString(R.string.displayed_time_work)
					timerText.text = convertTimeHourToString(state.timeTypeDisplayed.time)
				}
			}
		}
	}
}