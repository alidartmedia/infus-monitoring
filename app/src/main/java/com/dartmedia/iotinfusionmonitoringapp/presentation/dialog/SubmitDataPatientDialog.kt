package com.dartmedia.iotinfusionmonitoringapp.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.dartmedia.iotinfusionmonitoringapp.databinding.DialogClearDataPatientBinding
import com.dartmedia.iotinfusionmonitoringapp.databinding.DialogSubmitDataPatientBinding

class SubmitDataPatientDialog(context: Context): Dialog(context) {
    private val binding: DialogSubmitDataPatientBinding by lazy { DialogSubmitDataPatientBinding.inflate(layoutInflater) }

    private var listener: SubmitDataPatientDialogListener? = null

    fun setDialogListener(listener: SubmitDataPatientDialogListener) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        actionBtnYes()
        actionBtnNo()
    }

    private fun actionBtnYes() {
        binding.btnYes.setOnClickListener {
            listener?.onYes()
            dismiss()
        }
    }

    private fun actionBtnNo() {
        binding.btnNo.setOnClickListener {
            listener?.onNo()
        }
    }

    interface SubmitDataPatientDialogListener {
        fun onYes()
        fun onNo()
    }
}