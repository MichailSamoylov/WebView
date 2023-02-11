package com.app.webview.screens.webview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
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

	private lateinit var binding: FragmentWebViewBinding

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		binding = FragmentWebViewBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		arguments?.webUrl?.let { url ->
			binding.webview.webViewClient = object : WebViewClient() {

				override fun onPageFinished(view: WebView?, url: String?) {
					super.onPageFinished(view, url)
					CookieManager.getInstance().flush()
				}
			}
			binding.webview.loadUrl(url)
		}
	}
}