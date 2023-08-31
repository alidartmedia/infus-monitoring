package com.dartmedia.iotinfusionmonitoringapp.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dartmedia.iotinfusionmonitoringapp.R
import com.dartmedia.iotinfusionmonitoringapp.databinding.FragmentThirdDayBinding

class ThirdDayFragment : Fragment() {

    private val binding: FragmentThirdDayBinding by lazy { FragmentThirdDayBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}