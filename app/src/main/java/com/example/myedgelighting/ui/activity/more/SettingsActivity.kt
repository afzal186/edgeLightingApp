package com.example.myedgelighting.ui.activity.more

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myedgelighting.R
import com.example.myedgelighting.databinding.ActivitySettingsBinding
import com.example.myedgelighting.utils.SettingUtils.goToPrivacyPolicy
import com.example.myedgelighting.utils.SettingUtils.rateApp
import com.example.myedgelighting.utils.SettingUtils.shareApp

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = this.resources.getColor(R.color.settingbar)
        window.navigationBarColor = this.resources.getColor(R.color.bottomnav)
        binding.tvShare.setOnClickListener(View.OnClickListener { shareApp(this@SettingsActivity) })
        binding.tvRate.setOnClickListener(View.OnClickListener { rateApp(this@SettingsActivity) })
        binding.tvPrivacy.setOnClickListener(View.OnClickListener { goToPrivacyPolicy(this@SettingsActivity) })
        binding.backbutton.setOnClickListener {
            onBackPressed()
        }
    }
}