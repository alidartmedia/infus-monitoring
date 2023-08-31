package com.dartmedia.iotinfusionmonitoringapp.presentation.activity.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.patient.AddPatientRequest
import com.dartmedia.iotinfusionmonitoringapp.databinding.ActivityFormPatientBinding
import com.dartmedia.iotinfusionmonitoringapp.presentation.TokenViewModel
import com.dartmedia.iotinfusionmonitoringapp.presentation.activity.login.LoginActivity
import com.dartmedia.iotinfusionmonitoringapp.presentation.dialog.LoadingDialog
import com.dartmedia.iotinfusionmonitoringapp.presentation.dialog.SubmitDataPatientDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormPatientActivity : AppCompatActivity() {

    companion object {
        const val DEVICE_ID = "device_id"
    }

    private val binding: ActivityFormPatientBinding by lazy { ActivityFormPatientBinding.inflate(layoutInflater) }
    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(this@FormPatientActivity) }
    private val submitDataPatientDialog: SubmitDataPatientDialog by lazy { SubmitDataPatientDialog(this@FormPatientActivity) }
    private val formPatientViewModel: FormPatientViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpActionBar()
        actionBtnReset()
        actionBtnSubmit()
        actionBtnClear()
        observeViewModel()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setUpActionBar() {
        val deviceId = intent.getStringExtra(DEVICE_ID)
        supportActionBar?.apply {
            title = "Form Data Patient ($deviceId)"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun actionBtnReset() {
        binding.btnReset.setOnClickListener {
            binding.etNoRm.text = null
            binding.etFullName.text = null
            binding.etNoBed.text = null
            binding.etRoomName.text = null
        }
    }

    private fun actionBtnClear() {

    }

    private fun actionBtnSubmit() {
        binding.btnSubmit.setOnClickListener {
            val id = intent.getStringExtra(DEVICE_ID)
            val name = binding.etFullName.text.toString()
            val noRm = binding.etNoRm.text.toString()
            val room = binding.etRoomName.text.toString()
            val bed = binding.etNoBed.text.toString()

            if (noRm.isEmpty()) {
                Toast.makeText(this@FormPatientActivity, "No. RM cannot be empty.", Toast.LENGTH_SHORT).show()
            } else if (name.isEmpty()) {
                Toast.makeText(this@FormPatientActivity, "Full Name cannot be empty.", Toast.LENGTH_SHORT).show()
            } else if (room.isEmpty()) {
                Toast.makeText(this@FormPatientActivity, "Room Name cannot be empty.", Toast.LENGTH_SHORT).show()
            } else if (bed.isEmpty()) {
                Toast.makeText(this@FormPatientActivity, "No. Bed cannot be empty.", Toast.LENGTH_SHORT).show()
            } else {
                submitDataPatientDialog.show()
                submitDataPatientDialog.setDialogListener(object : SubmitDataPatientDialog.SubmitDataPatientDialogListener {

                    override fun onYes() {
                        val addPatientRequest = AddPatientRequest(
                            name,
                            room,
                            bed.toInt(),
                            noRm,
                            id!!
                        )
                        formPatientViewModel.addPatient(addPatientRequest)

                        submitDataPatientDialog.dismiss()
                    }

                    override fun onNo() {
                        submitDataPatientDialog.dismiss()
                    }

                })
            }
        }
    }

    private fun observeViewModel() {
        with(formPatientViewModel) {
            resultAdd.observe(this@FormPatientActivity) {
                finish()
                Toast.makeText(this@FormPatientActivity, it, Toast.LENGTH_SHORT).show()
            }

            error.observe(this@FormPatientActivity) {
                if (it.code == 401) {
                    tokenViewModel.deleteAccessToken()
                    tokenViewModel.deleteRefreshToken()

                    val i = Intent(this@FormPatientActivity, LoginActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }
                Toast.makeText(this@FormPatientActivity, it.message, Toast.LENGTH_SHORT).show()
            }

            isLoading.observe(this@FormPatientActivity) {
                if (it) {
                    loadingDialog.show()
                } else {
                    loadingDialog.dismiss()
                }
            }
        }
    }

}