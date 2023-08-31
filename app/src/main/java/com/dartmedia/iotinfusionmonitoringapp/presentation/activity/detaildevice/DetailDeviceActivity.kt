package com.dartmedia.iotinfusionmonitoringapp.presentation.activity.detaildevice

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.wifi.WifiManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dartmedia.iotinfusionmonitoringapp.R
import com.dartmedia.iotinfusionmonitoringapp.databinding.ActivityDetailDeviceBinding
import com.dartmedia.iotinfusionmonitoringapp.presentation.activity.form.FormPatientActivity
import com.dartmedia.iotinfusionmonitoringapp.presentation.activity.SetDeviceWifiActivity
import com.dartmedia.iotinfusionmonitoringapp.presentation.dialog.ClearDataPatientDialog
import com.dartmedia.iotinfusionmonitoringapp.presentation.dialog.LoadingDialog
import com.ekn.gruzer.gaugelibrary.Range
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DetailDeviceActivity : AppCompatActivity() {

    companion object {
        private const val CODE_PERMISSION_WIFI_STATE = 902
        private const val CODE_PERMISSION_FINE_LOCATION = 903
        private const val CODE_PERMISSION_COARSE_LOCATION = 904

        const val DEVICE_ID = "device_id"
    }

    private val binding: ActivityDetailDeviceBinding by lazy { ActivityDetailDeviceBinding.inflate(layoutInflater) }
    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(this@DetailDeviceActivity) }
    private val clearDataPatientDialog: ClearDataPatientDialog by lazy { ClearDataPatientDialog(this@DetailDeviceActivity) }
    private val detailDeviceViewModel: DetailDeviceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        checkPermission()
        setUpActionBar()
        setGauge()
        actionBtnSetDeviceWifi()
        actionBtnClearDataPatient()
        actionBtnSetDataPatient()
        actionBtnRetry()
//        initPageAdapter()
        getDeviceById()
        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_detail_device, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.set_wifi -> {
                val i = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    Intent(Settings.Panel.ACTION_WIFI)
                } else {
                    Intent(Settings.ACTION_WIFI_SETTINGS)
                }
                startActivity(i)

                return true
            }
            else -> {
                onBackPressed()

                return true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getWifiSSID()
        getDeviceById()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            CODE_PERMISSION_WIFI_STATE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this@DetailDeviceActivity, "Permission wifi state is required", Toast.LENGTH_SHORT).show()
                }
            }
            CODE_PERMISSION_FINE_LOCATION -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this@DetailDeviceActivity, "Permission fine location is required", Toast.LENGTH_SHORT).show()
                }
            }
            CODE_PERMISSION_COARSE_LOCATION -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this@DetailDeviceActivity, "Permission coarse location is required", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkPermission() {
        val permissionWifiState = ContextCompat.checkSelfPermission(this@DetailDeviceActivity, android.Manifest.permission.ACCESS_WIFI_STATE)
        val permissionFineLocation = ContextCompat.checkSelfPermission(this@DetailDeviceActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionCoarseLocation = ContextCompat.checkSelfPermission(this@DetailDeviceActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION)

        if (permissionWifiState != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@DetailDeviceActivity, arrayOf(android.Manifest.permission.ACCESS_WIFI_STATE),
                CODE_PERMISSION_WIFI_STATE
            )
        } else if (permissionFineLocation != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@DetailDeviceActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                CODE_PERMISSION_FINE_LOCATION
            )
        } else if (permissionCoarseLocation != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@DetailDeviceActivity, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                CODE_PERMISSION_COARSE_LOCATION
            )
        }
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = intent.getStringExtra(DEVICE_ID)
            elevation = 0f
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setGauge() {
//        val range1 = Range()
//        range1.color = Color.parseColor("#CE0000")
//        range1.from = 10.0
//        range1.to = 100.0
//
//        val range2 = Range()
//        range2.color = Color.parseColor("#E3E500")
//        range2.from = 101.0
//        range2.to = 370.0
//
//        val range3 = Range()
//        range3.color = Color.parseColor("#00B20B")
//        range3.from = 371.0
//        range3.to = 740.0
//
//        with(binding.layoutDetailDevice.gaugeWeight) {
//            addRange(range1)
//            addRange(range2)
//            addRange(range3)
//
//            minValue = 10.0
//            maxValue = 740.0
//            value = 740.0
//        }
    }

    private fun getWifiSSID() {
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager

        if (wifiManager.isWifiEnabled) {
            val wifiInfo = wifiManager.connectionInfo
            val ssid = wifiInfo.ssid

            if (!ssid.isNullOrEmpty() && ssid != "<unknown ssid>") {
                binding.layoutDetailDevice.btnSetDeviceWifi.isEnabled = ssid == "\"DUMMY_WIFI\""
            }
        }
    }

    private fun actionBtnSetDeviceWifi() {
        binding.layoutDetailDevice.btnSetDeviceWifi.setOnClickListener {
            val i = Intent(this@DetailDeviceActivity, SetDeviceWifiActivity::class.java)
            startActivity(i)
        }
    }

    private fun actionBtnClearDataPatient() {
        binding.layoutDetailDevice.btnClearDataPatient.setOnClickListener {
            clearDataPatientDialog.show()
        }

        clearDataPatientDialog.setDialogListener(object : ClearDataPatientDialog.ClearDataPatientDialogListener {
            override fun onYes() {
                Toast.makeText(this@DetailDeviceActivity, "Yes", Toast.LENGTH_SHORT).show()
                clearDataPatientDialog.dismiss()
            }

            override fun onNo() {
                clearDataPatientDialog.dismiss()
            }
        })
    }

    private fun actionBtnSetDataPatient() {
        binding.layoutDetailDevice.btnSetDataPatient.setOnClickListener {
            val id = intent.getStringExtra(DEVICE_ID)
            val i = Intent(this@DetailDeviceActivity, FormPatientActivity::class.java)
            i.putExtra(FormPatientActivity.DEVICE_ID, id)
            startActivity(i)
        }
    }

    private fun actionBtnRetry() {
        binding.layoutError.btnRetry.setOnClickListener {
            setGauge()
            getWifiSSID()
            getDeviceById()
        }
    }

//    private fun initPageAdapter() {
//        val fragment = mutableListOf(
//            CurrentDayFragment(),
//            SecondDayFragment(),
//            ThirdDayFragment(),
//            FourthDayFragment(),
//            FifthDayFragment()
//        )
//
//        val fragmentTitle = mutableListOf(
//            getCurrentDate(),
//            getDateBeforeCurrentDate(1000),
//            getDateBeforeCurrentDate(2000),
//            getDateBeforeCurrentDate(3000),
//            getDateBeforeCurrentDate(4000),
//        )
//
//        val sectionPagerAdapter = DetailDevicePagerAdapter(this, fragment)
//        binding.apply {
//            viewPager.adapter = sectionPagerAdapter
//
//            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//                tab.text = fragmentTitle[position]
//            }.attach()
//        }
//    }

    private fun getDeviceById() {
        val id = intent.getStringExtra(DEVICE_ID)
        detailDeviceViewModel.getDeviceById(id!!)
    }

    private fun observeViewModel() {
        with(detailDeviceViewModel) {
            resultDeviceById.observe(this@DetailDeviceActivity) {
                binding.apply {
                    layoutDetailDevice.root.visibility = View.VISIBLE
                    layoutError.root.visibility = View.GONE
                }

                binding.layoutDetailDevice.apply {
                    if (it.data?.device?.status == 1) {
                        ivIconStatus.setImageResource(R.drawable.mark_online)
                        tvDeviceStatus.text = "Online"
                        tvNoRm.text = it.data.device.noRm
                        tvPatientName.text = it.data.device.name
                        tvRoom.text = if (it.data.device.noBad == null) {
                            "R. ${it.data.device.roomName}"
                        } else {
                            "R. ${it.data.device.roomName} (Bed ${it.data.device.noBad})"
                        }
                    } else {
                        ivIconStatus.setImageResource(R.drawable.mark_offline)
                        tvDeviceStatus.text = "Offline"
                        tvNoRm.text = "-"
                        tvPatientName.text = "-"
                        tvRoom.text = "-"
                    }
                }
            }

            error.observe(this@DetailDeviceActivity) {
                binding.apply {
                    layoutDetailDevice.root.visibility = View.GONE
                    layoutError.root.visibility = View.VISIBLE
                }
                Toast.makeText(this@DetailDeviceActivity, it.message, Toast.LENGTH_SHORT).show()
            }

            isLoading.observe(this@DetailDeviceActivity) {
                if (it) {
                    if (!loadingDialog.isShowing) {
                        loadingDialog.show()
                    }
                } else {
                    loadingDialog.dismiss()
                }
            }
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yy")
        val result = Date(System.currentTimeMillis())
        return dateFormat.format(result)
    }

    private fun getDateBeforeCurrentDate(day: Int): String {
        val dateFormat = SimpleDateFormat("dd/MM/yy")
        val result = Date(System.currentTimeMillis() - day * 60 * 60 * 24)
        return dateFormat.format(result)
    }

}