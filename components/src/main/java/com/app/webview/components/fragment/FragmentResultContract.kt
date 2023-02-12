package com.app.webview.components.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment

interface FragmentResultContract<T> {

	val requestKey: String

	fun getResult(bundle: Bundle): T

	fun setResult(result: T): Bundle
}

fun <T> Fragment.setResultListener(contract: FragmentResultContract<T>, listener: (T) -> Unit) {
	parentFragmentManager.setFragmentResultListener(contract.requestKey, viewLifecycleOwner) { _, bundle ->
		listener(contract.getResult(bundle))
	}
}

fun <T> Fragment.setResult(contract: FragmentResultContract<T>, result: T) {
	parentFragmentManager.setFragmentResult(contract.requestKey, contract.setResult(result))
}