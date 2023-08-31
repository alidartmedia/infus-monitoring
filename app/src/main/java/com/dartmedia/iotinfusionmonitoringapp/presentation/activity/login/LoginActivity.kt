package com.dartmedia.iotinfusionmonitoringapp.presentation.activity.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dartmedia.iotinfusionmonitoringapp.databinding.ActivityLoginBinding
import com.dartmedia.iotinfusionmonitoringapp.presentation.activity.home.HomeActivity
import com.dartmedia.iotinfusionmonitoringapp.presentation.dialog.LoadingDialog
import com.dartmedia.iotinfusionmonitoringapp.presentation.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(this@LoginActivity) }
    private val loginViewModel: LoginViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        actionLogin()
        observeViewModel()
    }

    private fun actionLogin() {
        binding.btnLogin.setOnClickListener {
            val id = binding.etId.text.toString()
            val password = binding.etPassword.text.toString()

            if (id.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Id cannot be empty.", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Password cannot be empty.", Toast.LENGTH_SHORT).show()
            } else {
                loginViewModel.doLogin(id, password)
            }
        }
    }

    private fun observeViewModel() {
        with(loginViewModel) {
            resultLogin.observe(this@LoginActivity) {
                tokenViewModel.saveRefreshToken(it.data?.refreshToken!!)
                tokenViewModel.saveAccessToken(it.data.accessToken!!)

                val i = Intent(this@LoginActivity, HomeActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }

            error.observe(this@LoginActivity) {
                Toast.makeText(this@LoginActivity, it, Toast.LENGTH_SHORT).show()
            }

            isLoading.observe(this@LoginActivity) {
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