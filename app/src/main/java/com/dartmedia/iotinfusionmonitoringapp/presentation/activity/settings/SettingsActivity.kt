package com.dartmedia.iotinfusionmonitoringapp.presentation.activity.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.dartmedia.iotinfusionmonitoringapp.databinding.ActivitySettingsBinding
import com.dartmedia.iotinfusionmonitoringapp.presentation.activity.login.LoginActivity
import com.dartmedia.iotinfusionmonitoringapp.presentation.TokenViewModel
import com.dartmedia.iotinfusionmonitoringapp.presentation.dialog.LoadingDialog
import com.dartmedia.iotinfusionmonitoringapp.presentation.dialog.LogoutDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private val binding: ActivitySettingsBinding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }
    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(this@SettingsActivity) }
    private val logoutDialog: LogoutDialog by lazy { LogoutDialog(this@SettingsActivity) }
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpActionBar()
        actionBtnLogOut()
        actionLogoutDialog()
        observeViewModel()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Settings"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun actionBtnLogOut() {
        binding.layoutSettings.btnLogout.setOnClickListener {
            logoutDialog.show()
        }
    }

    private fun actionLogoutDialog() {
        logoutDialog.setLogoutDialogListener(object : LogoutDialog.LogoutDialogListener {
            override fun onLogOut() {
                logoutDialog.dismiss()
                tokenViewModel.refreshToken.observe(this@SettingsActivity) {
                    if (it != null) {
                        settingsViewModel.doLogout(it)
                    }
                }
            }

            override fun onCancel() {
                logoutDialog.dismiss()
            }

        })
    }

    private fun observeViewModel() {
        with(settingsViewModel) {
            resultLogout.observe(this@SettingsActivity) {
                tokenViewModel.deleteRefreshToken()
                tokenViewModel.deleteAccessToken()

                val i = Intent(this@SettingsActivity, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }

            error.observe(this@SettingsActivity) {
                if (it.code == 401) {
                    tokenViewModel.deleteAccessToken()
                    tokenViewModel.deleteRefreshToken()

                    val i = Intent(this@SettingsActivity, LoginActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }

                Toast.makeText(this@SettingsActivity, it.message, Toast.LENGTH_SHORT).show()
            }

            isLoading.observe(this@SettingsActivity) {
                if (it) {
                    if (!loadingDialog.isShowing) {
                        loadingDialog.show()
                    }
                } else {
                    loadingDialog.dismiss()
                }
            }
        }
    }

}