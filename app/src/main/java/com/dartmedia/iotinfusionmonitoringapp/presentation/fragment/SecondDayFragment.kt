package com.dartmedia.iotinfusionmonitoringapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dartmedia.iotinfusionmonitoringapp.R
import com.dartmedia.iotinfusionmonitoringapp.databinding.FragmentSecondDayBinding

class SecondDayFragment : Fragment() {

    private val binding: FragmentSecondDayBinding by lazy { FragmentSecondDayBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}