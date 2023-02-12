package com.app.webview.components.fragment

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

fun Fragment.addBackPressedListener(enabledCallback: Boolean = true, action: OnBackPressedCallback.() -> Unit): OnBackPressedCallback {
	val callback: OnBackPressedCallback = object : OnBackPressedCallback(enabledCallback) {
		override fun handleOnBackPressed() {
			action.invoke(this)
		}
	}
	requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
	return callback
}