package com.dartmedia.iotinfusionmonitoringapp.presentation.activity.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeViewPagerAdapter(
    fa: FragmentActivity,
    private val fragment: MutableList<Fragment>
) : FragmentStateAdapter(fa){

    override fun getItemCount(): Int = fragment.size

    override fun createFragment(position: Int): Fragment = fragment[position]

}