package com.example.myedgelighting.ui.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.BatteryManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.myedgelighting.R
import com.example.myedgelighting.custom_edge_view.EdgeOverlayView
import com.example.myedgelighting.databinding.FragmentSingleColorBinding
import com.example.myedgelighting.ui.activity.dashboard.DashboardActivity
import com.example.myedgelighting.utils.CommonKeys
import com.example.myedgelighting.utils.CommonKeys.ColorMode
import com.example.myedgelighting.utils.MySharedPrefs


// SingleColorFragment.kt
class SingleColorFragment : Fragment() {
    private lateinit var binding: FragmentSingleColorBinding
    private lateinit var edgeOverlayView: EdgeOverlayView


    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {


        }
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(receiver, IntentFilter("colorChanged"))
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSingleColorBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibGradient1.setOnClickListener {

            val selectedColor =
                context?.resources?.getColor(R.color.single1) // Replace with the selected color
            // Save the selected color in preferences
            if (selectedColor != null) {
                MySharedPrefs(requireContext()).setIntPref(CommonKeys.SelectedColor, selectedColor)
            }

            // Broadcast an intent to notify DashboardActivity
            val intent = Intent("colorChanged")
            intent.putExtra("color", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }

        binding.ibGradient2.setOnClickListener {
            val selectedColor = context?.resources?.getColor(R.color.single2)
            // Save the selected color in preferences
            if (selectedColor != null) {
                MySharedPrefs(requireContext()).setIntPref(CommonKeys.SelectedColor, selectedColor)
            }

            // Broadcast an intent to notify DashboardActivity
            val intent = Intent("colorChanged")
            intent.putExtra("color", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        binding.ibGradient3.setOnClickListener {
            val selectedColor = context?.resources?.getColor(R.color.single3)
            // Save the selected color in preferences
            if (selectedColor != null) {
                MySharedPrefs(requireContext()).setIntPref(CommonKeys.SelectedColor, selectedColor)
            }

            // Broadcast an intent to notify DashboardActivity
            val intent = Intent("colorChanged")
            intent.putExtra("color", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }


        binding.ibGradient4.setOnClickListener {
            val selectedColor = context?.resources?.getColor(R.color.single4)
            // Save the selected color in preferences
            if (selectedColor != null) {
                MySharedPrefs(requireContext()).setIntPref(CommonKeys.SelectedColor, selectedColor)
            }

            // Broadcast an intent to notify DashboardActivity
            val intent = Intent("colorChanged")
            intent.putExtra("color", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        binding.ibGradient5.setOnClickListener {
            val selectedColor = context?.resources?.getColor(R.color.single5)
            // Save the selected color in preferences
            if (selectedColor != null) {
                MySharedPrefs(requireContext()).setIntPref(CommonKeys.SelectedColor, selectedColor)
            }

            // Broadcast an intent to notify DashboardActivity
            val intent = Intent("colorChanged")
            intent.putExtra("color", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        binding.ibGradient6.setOnClickListener {
            val selectedColor = context?.resources?.getColor(R.color.single6)
            // Save the selected color in preferences
            if (selectedColor != null) {
                MySharedPrefs(requireContext()).setIntPref(CommonKeys.SelectedColor, selectedColor)
            }

            // Broadcast an intent to notify DashboardActivity
            val intent = Intent("colorChanged")
            intent.putExtra("color", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        binding.ibGradient7.setOnClickListener {
            val selectedColor = context?.resources?.getColor(R.color.single7)
            // Save the selected color in preferences
            if (selectedColor != null) {
                MySharedPrefs(requireContext()).setIntPref(CommonKeys.SelectedColor, selectedColor)
            }

            // Broadcast an intent to notify DashboardActivity
            val intent = Intent("colorChanged")
            intent.putExtra("color", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        binding.ibGradient8.setOnClickListener {
            val selectedColor = context?.resources?.getColor(R.color.single8)
            // Save the selected color in preferences
            if (selectedColor != null) {
                MySharedPrefs(requireContext()).setIntPref(CommonKeys.SelectedColor, selectedColor)
            }

            // Broadcast an intent to notify DashboardActivity
            val intent = Intent("colorChanged")
            intent.putExtra("color", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
        }
        binding.ibGradient9.setOnClickListener {
            val selectedColor = context?.resources?.getColor(R.color.single9)
            // Save the selected color in preferences
            if (selectedColor != null) {
                MySharedPrefs(requireContext()).setIntPref(CommonKeys.SelectedColor, selectedColor)
            }

            // Broadcast an intent to notify DashboardActivity
            val intent = Intent("colorChanged")
            intent.putExtra("color", selectedColor)
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)

            binding.ibGradient10.setOnClickListener {
                val selectedColor = context?.resources?.getColor(R.color.single10)
                // Save the selected color in preferences
                if (selectedColor != null) {
                    MySharedPrefs(requireContext()).setIntPref(
                        CommonKeys.SelectedColor,
                        selectedColor
                    )
                }

                // Broadcast an intent to notify DashboardActivity
                val intent = Intent("colorChanged")
                intent.putExtra("color", selectedColor)
                LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
            }
        }

//class MyService : Service() {
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        // Get the action from the intent
//        val action = intent?.action
//
//        // Check if the action matches specific conditions
//        if (action == Intent.ACTION_POWER_CONNECTED || action == "ACTION_NOTIFICATION_RECEIVED") {
//            val selectedColor = MySharedPrefs(applicationContext).getIntPref(CommonKeys.SelectedColor, Color.RED)
//
//            // Use the selected color in your service logic
//            // For example, you can update a notification color or perform other tasks
//        }
//
//        return START_STICKY
//    }
//
//    // Other overridden methods...
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//}
    }
}


//binding.ibGradient5.setOnClickListener {
//    val selectedColor = context?.resources?.getColor(R.color.single5)
//    if (selectedColor != null) {
//        // Save the selected color in preferences
//        MySharedPrefs(requireContext()).setIntPref(CommonKeys.SelectedColor, selectedColor)
//
//        // Broadcast the color change event
//        val colorChangedIntent = Intent("colorChanged")
//        colorChangedIntent.putExtra("color", selectedColor)
//        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(colorChangedIntent)
//    }
//}


//val receiver: BroadcastReceiver = object : BroadcastReceiver() {
//    override fun onReceive(context: Context?, intent: Intent?) {
//        if (intent?.action == "colorChanged") {
//            val selectedColor = intent.getIntExtra("color", Color.RED)
//            // Use the selected color as needed
//            // Add your code here...
//        }
//    }
//}


