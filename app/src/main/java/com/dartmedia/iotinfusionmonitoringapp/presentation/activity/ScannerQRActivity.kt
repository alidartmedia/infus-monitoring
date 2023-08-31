package com.dartmedia.iotinfusionmonitoringapp.presentation.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import com.dartmedia.iotinfusionmonitoringapp.databinding.ActivityScannerQrBinding
import com.dartmedia.iotinfusionmonitoringapp.presentation.activity.detaildevice.DetailDeviceActivity
import com.dartmedia.iotinfusionmonitoringapp.presentation.activity.home.HomeActivity
import com.dartmedia.iotinfusionmonitoringapp.presentation.activity.qr.Barcode
import com.google.gson.Gson

class ScannerQRActivity : AppCompatActivity() {
    companion object {
        private const val CODE_PERMISSION_CAMERA = 901
    }

    private val binding: ActivityScannerQrBinding by lazy { ActivityScannerQrBinding.inflate(layoutInflater) }
    private val codeScanner: CodeScanner by lazy { CodeScanner(this, binding.scannerView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        checkPermission()
        codeScanner()
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        super.onPause()
        codeScanner.releaseResources()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            CODE_PERMISSION_CAMERA -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this@ScannerQRActivity, "Permission camera is required", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkPermission() {
        val permission = ContextCompat.checkSelfPermission(this@ScannerQRActivity, android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@ScannerQRActivity, arrayOf(android.Manifest.permission.CAMERA), CODE_PERMISSION_CAMERA)
        }
    }

    private fun codeScanner() {
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE

            decodeCallback = DecodeCallback {
                runOnUiThread {
//                    val i = Intent(this@ScannerQRActivity, HomeActivity::class.java)
//                    i.putExtra(HomeActivity.EXTRA_DATA_STRING, it.toString())
//                    setResult(Activity.RESULT_OK, i)
//                    finish()

                    convertDataBarcode(it.text)
                }
            }
        }

        codeScanner.startPreview()
    }

    private fun convertDataBarcode(dataBarcode: String) {
        val jsonString = Gson().fromJson(dataBarcode, String::class.java)
        val device = Gson().fromJson(jsonString, Barcode::class.java)

        val i = Intent(this@ScannerQRActivity, DetailDeviceActivity::class.java)
        i.putExtra(DetailDeviceActivity.DEVICE_ID, device.deviceId)
        startActivity(i)
    }
}