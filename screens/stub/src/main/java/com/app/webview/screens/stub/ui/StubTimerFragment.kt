package com.app.webview.screens.stub.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.app.webview.screens.stub.R
import com.app.webview.screens.stub.databinding.FragmentStubTimerBinding
import com.app.webview.screens.stub.presentation.StubTimerState
import com.app.webview.screens.stub.presentation.StubTimerViewModel
import com.app.webview.screens.stub.presentation.TimerMode
import java.text.SimpleDateFormat
import java.util.Locale
import org.koin.androidx.viewmodel.ext.android.viewModel

class StubTimerFragment : Fragment(R.layout.fragment_stub_timer) {

	companion object {

		fun newInstance() = StubTimerFragment()
	}

	private val viewModel: StubTimerViewModel by viewModel()
	private lateinit var binding: FragmentStubTimerBinding

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentStubTimerBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setObservers()
		setListeners()
	}

	private fun setListeners() {
		with(binding) {
			prepValueTextEdit.doAfterTextChanged { text -> viewModel.setPredPeriod(text.toString()) }
			workValueTextEdit.doAfterTextChanged { text -> viewModel.setWorkPeriod(text.toString()) }
			restValueTextEdit.doAfterTextChanged { text -> viewModel.setRestPeriod(text.toString()) }
			roundsValueTextEdit.doAfterTextChanged { text -> viewModel.setRoundsCount(text.toString()) }
			button.setOnClickListener { viewModel.startWorking() }
		}
	}

	private fun setObservers() {
		viewModel.state.observe(viewLifecycleOwner, ::handleState)
	}

	private fun handleState(state: StubTimerState) {
		if (state is StubTimerState.Content) {
			if (state.timerMode is TimerMode.Settings) {
				with(binding) {
					prepValueTextEdit.setText(convertTimeHourToString(state.timerMode.pred))
					workValueTextEdit.setText(convertTimeHourToString(state.timerMode.work))
					restValueTextEdit.setText(convertTimeHourToString(state.timerMode.rest))
					val amountTime = (state.timerMode.pred + state.timerMode.work + state.timerMode.rest) * state.timerMode.rounds
					if (amountTime > StubTimerViewModel.HOUR) {
						if (amountTime > StubTimerViewModel.DAY)
							amountWorkTimeText.text = convertTimeDayToString(StubTimerViewModel.DAY)
						else
							amountWorkTimeText.text = convertTimeDayToString(amountTime)
					} else {
						amountWorkTimeText.text = convertTimeHourToString(amountTime)
					}
				}
			}
		}
	}

	private fun convertTimeHourToString(date: Long): String =
		SimpleDateFormat("mm:ss", Locale("ru")).format(date)

	private fun convertTimeDayToString(date: Long): String =
		SimpleDateFormat("kk:mm:ss", Locale("ru")).format(date)
}