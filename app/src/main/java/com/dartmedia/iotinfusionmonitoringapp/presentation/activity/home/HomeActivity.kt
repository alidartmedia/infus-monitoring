package com.dartmedia.iotinfusionmonitoringapp.presentation.activity.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.dartmedia.iotinfusionmonitoringapp.R
import com.dartmedia.iotinfusionmonitoringapp.databinding.ActivityHomeBinding
import com.dartmedia.iotinfusionmonitoringapp.presentation.activity.ScannerQRActivity
import com.dartmedia.iotinfusionmonitoringapp.presentation.activity.settings.SettingsActivity
import com.dartmedia.iotinfusionmonitoringapp.presentation.fragment.alldevice.AllDeviceFragment
import com.dartmedia.iotinfusionmonitoringapp.presentation.fragment.offlinedevice.OfflineFragment
import com.dartmedia.iotinfusionmonitoringapp.presentation.fragment.onlinedevice.OnlineFragment
import com.dartmedia.iotinfusionmonitoringapp.presentation.TokenViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA_STRING = "extra data string"
    }

    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val tokenViewModel: TokenViewModel by viewModels()
    private val startActivityScanBarcodeWithResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data?.getStringExtra(EXTRA_DATA_STRING)
//            Log.d("TESTING", data!!.substring(1, data.length - 1))
            if (data != null) {
                convertDataBarcode(data)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpActionBar()
        initPageAdapter()
        actionFab()
//        convertDataBarcode("{\n  \"device_id\" : \"Infusion_DM1\",\n  \"serial_number\" : \"01\",\n  \"date_production\" : \"14/07/2023\"\n}")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                val i = Intent(this@HomeActivity, SettingsActivity::class.java)
                startActivity(i)

                return true
            }
            else -> return true
        }
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Infus Monitoring (OFFICER)"
            elevation = 0f
        }
    }

    private fun initPageAdapter() {
        val fragment = mutableListOf(
            AllDeviceFragment(),
            OnlineFragment(),
            OfflineFragment()
        )

        val fragmentTitle = mutableListOf(
            getString(R.string.all),
            getString(R.string.online),
            getString(R.string.offline)
        )

        val sectionPagerAdapter = HomeViewPagerAdapter(this, fragment)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = fragmentTitle[position]
            }.attach()
        }
    }

    private fun actionFab() {
        binding.fabScanQr.setOnClickListener {
            val i = Intent(this@HomeActivity, ScannerQRActivity::class.java)
//            startActivityScanBarcodeWithResult.launch(i)
            startActivity(i)
        }
    }

    private fun convertDataBarcode(dataBarcode: String) {
//        val jsonString = Gson().fromJson(dataBarcode, String::class.java)
//        val device = Gson().fromJson(jsonString, Device::class.java)
//
//        println(device)
    }

}