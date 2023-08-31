package com.dartmedia.iotinfusionmonitoringapp.presentation.activity.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import com.dartmedia.iotinfusionmonitoringapp.R
import com.dartmedia.iotinfusionmonitoringapp.databinding.ActivitySplashScreenBinding
import com.dartmedia.iotinfusionmonitoringapp.presentation.activity.home.HomeActivity
import com.dartmedia.iotinfusionmonitoringapp.presentation.activity.login.LoginActivity
import com.dartmedia.iotinfusionmonitoringapp.presentation.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val binding: ActivitySplashScreenBinding by lazy { ActivitySplashScreenBinding.inflate(layoutInflater) }
    private val tokenViewModel: TokenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observeTokenViewModel()
    }

    private fun doLogin() {
        Handler(mainLooper).postDelayed({
            val i = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            startActivity(i).also { finish() }
            overridePendingTransition(0, R.anim.fade_out)
        }, 3500)
    }

    private fun doIntentToHome() {
        Handler(mainLooper).postDelayed({
            val i = Intent(this@SplashScreenActivity, HomeActivity::class.java)
            startActivity(i).also { finish() }
            overridePendingTransition(0, R.anim.fade_out)
        }, 3500)
    }

    private fun observeTokenViewModel() {
        with(tokenViewModel) {
            refreshToken.observe(this@SplashScreenActivity) {
                if (it != null) {
                    doIntentToHome()
                } else {
                    doLogin()
                }
            }
        }
    }
}