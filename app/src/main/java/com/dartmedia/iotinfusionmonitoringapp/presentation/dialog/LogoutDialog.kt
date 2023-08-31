package com.dartmedia.iotinfusionmonitoringapp.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import com.dartmedia.iotinfusionmonitoringapp.databinding.DialogLoadingBinding
import com.dartmedia.iotinfusionmonitoringapp.databinding.DialogLogoutBinding

class LogoutDialog(context: Context): Dialog(context) {

    private val binding: DialogLogoutBinding by lazy { DialogLogoutBinding.inflate(layoutInflater) }

    private var listener: LogoutDialogListener? = null

    fun setLogoutDialogListener(listener: LogoutDialogListener) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        actionBtnLogOut()
        actionBtnCancel()
    }

    private fun actionBtnLogOut() {
        binding.btnLogout.setOnClickListener {
            listener?.onLogOut()
            dismiss()
        }
    }

    private fun actionBtnCancel() {
        binding.btnCancel.setOnClickListener {
            listener?.onCancel()
        }
    }

    interface LogoutDialogListener {
        fun onLogOut()
        fun onCancel()
    }

}