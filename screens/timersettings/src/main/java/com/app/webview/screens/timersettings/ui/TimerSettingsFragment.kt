package com.app.webview.screens.timersettings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.app.webview.components.fragment.setResultListener
import com.app.webview.components.stub.trainings.contracts.TrainingsResultContract
import com.app.webview.components.timeconverter.DAY
import com.app.webview.components.timeconverter.HOUR
import com.app.webview.components.timeconverter.convertTimeDayToString
import com.app.webview.components.timeconverter.convertTimeHourToString
import com.app.webview.screens.timersettings.R
import com.app.webview.screens.timersettings.databinding.FragmentTimerSettingsBinding
import com.app.webview.screens.timersettings.presentation.TimerSettingsState
import com.app.webview.screens.timersettings.presentation.TimerSettingsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TimerSettingsFragment : Fragment(R.layout.fragment_timer_settings) {

	companion object {

		fun newInstance() = TimerSettingsFragment()
	}

	private val viewModel: TimerSettingsViewModel by viewModel()
	private lateinit var binding: FragmentTimerSettingsBinding

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentTimerSettingsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setObservers()
		setListeners()
		setResultListener(TrainingsResultContract) {
			viewModel.onSelectedTraining(it)
		}
	}

	private fun setListeners() {
		with(binding) {
			prepValueTextEdit.doAfterTextChanged { text -> viewModel.setPredPeriod(text.toString()) }
			workValueTextEdit.doAfterTextChanged { text -> viewModel.setWorkPeriod(text.toString()) }
			restValueTextEdit.doAfterTextChanged { text -> viewModel.setRestPeriod(text.toString()) }
			roundsValueTextEdit.doAfterTextChanged { text -> viewModel.setRoundsCount(text.toString()) }
			buttonTrainings.setOnClickListener { viewModel.navigateToTrainingsScreen() }
			button.setOnClickListener { viewModel.startWorking() }
		}
	}

	private fun setObservers() {
		viewModel.state.observe(viewLifecycleOwner, ::handleState)
	}

	private fun handleState(state: TimerSettingsState) {
		if (state is TimerSettingsState.Content) {
			with(binding) {
				prepValueTextEdit.setText(convertTimeHourToString(state.pred))
				workValueTextEdit.setText(convertTimeHourToString(state.work))
				restValueTextEdit.setText(convertTimeHourToString(state.rest))
				val amountTime = (state.pred + state.work + state.rest) * state.rounds
				if (amountTime > HOUR) {
					if (amountTime > DAY)
						amountWorkTimeText.text = convertTimeDayToString(DAY)
					else
						amountWorkTimeText.text = convertTimeDayToString(amountTime)
				} else {
					amountWorkTimeText.text = convertTimeHourToString(amountTime)
				}
			}
		}
	}
}