package com.dartmedia.iotinfusionmonitoringapp.presentation.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.dartmedia.iotinfusionmonitoringapp.databinding.DialogLoadingBinding

class LoadingDialog(context: Context): Dialog(context) {

    private val binding: DialogLoadingBinding by lazy { DialogLoadingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)
    }

}