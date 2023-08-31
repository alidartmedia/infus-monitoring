package com.dartmedia.iotinfusionmonitoringapp.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.dartmedia.iotinfusionmonitoringapp.databinding.ActivitySetDeviceWifiBinding

class SetDeviceWifiActivity : AppCompatActivity() {

    private val binding: ActivitySetDeviceWifiBinding by lazy { ActivitySetDeviceWifiBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpActionBar()
        showWeb()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Infus_DM1"
            elevation = 0f
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun showWeb() {
        with(binding.webView) {
            webViewClient = WebViewClient()
            loadUrl("http://192.168.4.1/")
        }
    }

}