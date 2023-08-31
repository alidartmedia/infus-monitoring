package com.dartmedia.iotinfusionmonitoringapp.presentation.fragment.alldevice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dartmedia.iotinfusionmonitoringapp.R
import com.dartmedia.iotinfusionmonitoringapp.databinding.LayoutRvDeviceBinding
import com.dartmedia.iotinfusionmonitoringapp.domain.main.model.Device

class AllDeviceAdapter : PagingDataAdapter<Device, AllDeviceAdapter.ListViewHolder>(DIFF_CALLBACK) {

    private var onItemClickCallback: OnItemClickCallback? = null

    interface OnItemClickCallback {
        fun onItemClicked(data: Device)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(private val binding: LayoutRvDeviceBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Device) {
            with(binding) {
                tvDeviceName.text = data.deviceId
                if (data.status == 1) {
                    tvDeviceStatus.text = "Online"
                    tvRoom.text = "R. ${data.roomName} (Bed ${data.noBad})"
                    ivIconStatus.setImageResource(R.drawable.mark_online)
                } else {
                    tvDeviceStatus.text = "Offline"
                    tvRoom.text = null
                    ivIconStatus.setImageResource(R.drawable.mark_offline)
                }
            }
        }

    }

    override fun onBindViewHolder(holder: AllDeviceAdapter.ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
            holder.itemView.setOnClickListener { onItemClickCallback?.onItemClicked(data) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllDeviceAdapter.ListViewHolder {
        val binding = LayoutRvDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Device>() {
            override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
                return oldItem.deviceId == newItem.deviceId
            }
        }
    }

}