package com.example.myedgelighting.ui.activity.main

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myedgelighting.databinding.ActivityMainBinding
import com.example.myedgelighting.ui.activity.dashboard.DashboardActivity
import com.example.myedgelighting.utils.CommonKeys
import com.example.myedgelighting.utils.MySharedPrefs
import com.example.myedgelighting.utils.SettingUtils.showToast


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val REQUEST_OVERLAY_PERMISSION = 1
    private lateinit var binding: ActivityMainBinding


    companion object {
        const val PERMISSION_REQUEST_CODE = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

//        binding.chargingSwitch.isChecked = isServiceRunning

        binding.clAlwaysDisplay.setOnClickListener {
            requestOverlayPermission()
        }
        //setting switch checked or not according to permission status
        binding.alwaysDisplaySwitch.isChecked = Settings.canDrawOverlays(this)
        binding.alwaysDisplaySwitch.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                requestOverlayPermission()
            }
        }

    }

    private fun requestOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            val intent =
                Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION)
        } else {
            val intent = Intent(this@MainActivity, DashboardActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_OVERLAY_PERMISSION) {
            // Save the switch state in SharedPreferences
            MySharedPrefs(this).setBoolPref(CommonKeys.OVERLAY_KEY, Settings.canDrawOverlays(this))
            // Update the switch state
            if (Settings.canDrawOverlays(this)) {
                showToast(this, "Draw over other apps permission granted")
                val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                startActivity(intent)
            } else {
                showToast(this, "Overlay permission denied")
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}






