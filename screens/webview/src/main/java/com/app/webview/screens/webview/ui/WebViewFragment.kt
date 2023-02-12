package com.app.webview.screens.webview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.app.webview.components.fragment.addBackPressedListener
import com.app.webview.screens.webview.R
import com.app.webview.screens.webview.databinding.FragmentWebViewBinding

private const val WEB_URL = "WEB_URL"

var Bundle.webUrl
	get() = getString(WEB_URL)
	set(value) = putString(WEB_URL, value)

class WebViewFragment : Fragment(R.layout.fragment_web_view) {

	companion object {

		fun newInstance(webUrl: String) =
			WebViewFragment().apply {
				val bundle = Bundle()
				bundle.webUrl = webUrl
				arguments = bundle
			}
	}

	private object WebViewFragmentClient : WebViewClient() {

		val localPagesStack = mutableListOf<String>()
		override fun onPageFinished(view: WebView?, url: String?) {
			super.onPageFinished(view, url)
			url?.let { localPagesStack.add(it) }
			CookieManager.getInstance().flush()
		}
	}

	private lateinit var binding: FragmentWebViewBinding

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentWebViewBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		addBackPressedListener {
			if (WebViewFragmentClient.localPagesStack.size != 1) {
				WebViewFragmentClient.localPagesStack.removeLast()
				binding.webview.loadUrl(WebViewFragmentClient.localPagesStack.last())
			}
		}
		arguments?.webUrl?.let { url ->
			binding.webview.webViewClient = WebViewFragmentClient
			binding.webview.loadUrl(url)
		}
	}
}