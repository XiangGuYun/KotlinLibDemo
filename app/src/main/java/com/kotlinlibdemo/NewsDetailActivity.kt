package com.kotlinlibdemo

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import com.kotlinlib.activity.WebActivity
import com.kotlinlib.other.LayoutId
import kotlinx.android.synthetic.main.activity_news_detail.*

@LayoutId(R.layout.activity_news_detail)
class NewsDetailActivity : WebActivity() {

    override fun init(bundle: Bundle?) {
        webUrl = intent.getStringExtra(WEB_URL)
        ring.setViewColor("#666666".color())
        ring.show()
        ring.startAnim()
        initWebView(R.id.container, object : WebViewListener {
            override fun onLoadResource(view: WebView?, url: String?) {
                "load resource".logD("web")

            }

            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                "received error".logD("web")
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                "page started".logD("web")

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                "page finished".logD("web")
                ring.stopAnim()
                ring.hide()
            }

        })

    }
}
