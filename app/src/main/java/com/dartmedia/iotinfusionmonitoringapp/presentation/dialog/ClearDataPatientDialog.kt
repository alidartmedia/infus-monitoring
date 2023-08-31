package com.dartmedia.iotinfusionmonitoringapp.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.dartmedia.iotinfusionmonitoringapp.databinding.DialogClearDataPatientBinding

class ClearDataPatientDialog(context: Context): Dialog(context) {
    private val binding: DialogClearDataPatientBinding by lazy { DialogClearDataPatientBinding.inflate(layoutInflater) }

    private var listener: ClearDataPatientDialogListener? = null

    fun setDialogListener(listener: ClearDataPatientDialogListener) {
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

    interface ClearDataPatientDialogListener {
        fun onYes()
        fun onNo()
    }
}