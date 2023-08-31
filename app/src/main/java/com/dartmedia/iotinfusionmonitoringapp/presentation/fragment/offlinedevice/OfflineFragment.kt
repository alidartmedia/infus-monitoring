package com.dartmedia.iotinfusionmonitoringapp.presentation.fragment.offlinedevice

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dartmedia.iotinfusionmonitoringapp.databinding.FragmentOfflineBinding
import com.dartmedia.iotinfusionmonitoringapp.domain.main.model.Device
import com.dartmedia.iotinfusionmonitoringapp.presentation.TokenViewModel
import com.dartmedia.iotinfusionmonitoringapp.presentation.activity.detaildevice.DetailDeviceActivity
import com.dartmedia.iotinfusionmonitoringapp.presentation.activity.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OfflineFragment : Fragment() {

    private val binding: FragmentOfflineBinding by lazy { FragmentOfflineBinding.inflate(layoutInflater) }
    private val allOfflineDeviceAdapter: AllOfflineDeviceAdapter by lazy { AllOfflineDeviceAdapter() }
    private val allOfflineDeviceViewModel: AllOfflineDeviceViewModel by viewModels()
    private val tokenViewModel: TokenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        allOfflineDeviceViewModel.getCountAllOfflineDevice()
        actionOnItem()
        actionBtnRetry()
        observeViewModel()
    }

    private fun initRecyclerView() {
        with(binding.rvDevice) {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = allOfflineDeviceAdapter
        }
    }

    private fun actionOnItem() {
        allOfflineDeviceAdapter.setOnItemClickCallback(object : AllOfflineDeviceAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Device) {
                val i = Intent(requireActivity(), DetailDeviceActivity::class.java)
                i.putExtra(DetailDeviceActivity.DEVICE_ID, data.deviceId)
                startActivity(i)
            }
        })
    }

    private fun actionBtnRetry() {
        binding.btnRetry.setOnClickListener {
            allOfflineDeviceViewModel.getCountAllOfflineDevice()
            binding.btnRetry.isVisible = false
        }
    }

    private fun observeViewModel() {
        with(allOfflineDeviceViewModel) {
            resultCountAllOfflineDevice.observe(requireActivity()) {
                binding.apply {
                    tvCounter.text = "Offline Device - ${it.count}"
                    tvCounter.visibility = View.VISIBLE
                    divider1.visibility = View.VISIBLE
                    divider2.visibility = View.VISIBLE
                }

                collectDataPagination()
            }

            isLoading.observe(requireActivity()) {
                binding.progressBar.isVisible = it
            }

            error.observe(requireActivity()) {
                if (it.code == 401) {
                    tokenViewModel.deleteAccessToken()
                    tokenViewModel.deleteRefreshToken()

                    val i = Intent(requireActivity(), LoginActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                } else {
                    binding.apply {
                        btnRetry.visibility = View.VISIBLE
                        tvCounter.visibility = View.GONE
                        divider1.visibility = View.GONE
                        divider2.visibility = View.GONE
                    }
                }

                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Show list device in recycler view using paging 3
     */
    private fun collectDataPagination() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            allOfflineDeviceViewModel.getAllOfflineDevicePagination.collectLatest {
                allOfflineDeviceAdapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            allOfflineDeviceAdapter.loadStateFlow.collect {
                val state = it.refresh

                binding.apply {
                    progressBar.isVisible = state is LoadState.Loading
                    btnRetry.isVisible = state is LoadState.Error
                }
            }
        }
    }

}