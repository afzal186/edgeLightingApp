package com.example.myedgelighting.ui.activity.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myedgelighting.R
import com.example.myedgelighting.databinding.ActivitySplashBinding
import com.example.myedgelighting.ui.activity.dashboard.DashboardActivity
import com.example.myedgelighting.ui.activity.main.MainActivity

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /* // Create a blinking animation
         val blinkAnimation = AlphaAnimation(1f, 0f)
         blinkAnimation.duration = 500 // Duration of each blink in milliseconds
         blinkAnimation.repeatMode = Animation.REVERSE
         blinkAnimation.repeatCount = Animation.INFINITE

         // Start the animation
         binding.imageView.startAnimation(blinkAnimation)*/
        /* // Create a rotating animation
         val rotateAnimation = RotateAnimation(
             0f, // From angle (degrees)
             360f, // To angle (degrees)
             Animation.RELATIVE_TO_SELF, 0.5f, // Pivot X coordinate (relative to view)
             Animation.RELATIVE_TO_SELF, 0.5f  // Pivot Y coordinate (relative to view)
         )

         rotateAnimation.duration = 1000 // Duration of the rotation in milliseconds
         rotateAnimation.repeatCount = Animation.INFINITE // Repeat indefinitely

         // Start the animation
         binding.imageView.startAnimation(rotateAnimation)*/
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val window = this.window
        window.navigationBarColor = this.resources.getColor(R.color.black)


        binding.startBtn.setOnClickListener {
            checkAndRequestPermissions()
        }


    }


    private fun checkAndRequestPermissions() {
        val permissionsToRequest = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECEIVE_SMS
        )

        val permissionsNotGranted = permissionsToRequest.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsNotGranted.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsNotGranted.toTypedArray(),
                MainActivity.PERMISSION_REQUEST_CODE
            )
        } else {
            if (Settings.canDrawOverlays(this)) {
                val intent = Intent(this@SplashActivity, DashboardActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MainActivity.PERMISSION_REQUEST_CODE -> {
                if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    if (Settings.canDrawOverlays(this)) {
                        val intent = Intent(this@SplashActivity, DashboardActivity::class.java)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}