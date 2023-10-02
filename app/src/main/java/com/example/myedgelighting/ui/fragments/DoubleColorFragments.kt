package com.example.myedgelighting.ui.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.myedgelighting.R
import com.example.myedgelighting.custom_edge_view.EdgeOverlayView
import com.example.myedgelighting.databinding.FragmentDoubleColorFragmentsBinding

import com.example.myedgelighting.utils.CommonKeys
import com.example.myedgelighting.utils.MySharedPrefs


class DoubleColorFragments : Fragment() {
    private lateinit var binding: FragmentDoubleColorFragmentsBinding
    private lateinit var edgeOverlayView: EdgeOverlayView
    private lateinit var mySharedPrefs: MySharedPrefs
    private var displayColorMode: Int = 4
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
        // Inflate the layout for this fragment
        binding = FragmentDoubleColorFragmentsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.ibGradient1.setOnClickListener {
            val selectedColor =
                context?.resources?.getColor(R.color.doubleColor4) // Replace with the selected color
            val selectedColor1 =
                context?.resources?.getColor(R.color.doubleColor1) // Replace with the selected color
            val selectedColor2 =
                context?.resources?.getColor(R.color.doubleColor4) // Replace with the selected color
            // Save the selected color in preferences
            ShowSaveColor(selectedColor!!, selectedColor1!!, selectedColor2!!)
        }

        binding.ibGradient2.setOnClickListener {
            val selectedColor =
                context?.resources?.getColor(R.color.doubleColor3) // Replace with the selected color
            val selectedColor1 =
                context?.resources?.getColor(R.color.doubleColor4) // Replace with the selected color
            val selectedColor2 =
                context?.resources?.getColor(R.color.doubleColor4) // Replace with the selected color
            // Save the selected color in preferences
            ShowSaveColor(selectedColor!!, selectedColor1!!, selectedColor2!!)
        }

        binding.ibGradient3.setOnClickListener {
            val selectedColor =
                context?.resources?.getColor(R.color.doubleColor6) // Replace with the selected color
            val selectedColor1 =
                context?.resources?.getColor(R.color.doubleColor5) // Replace with the selected color
            val selectedColor2 =
                context?.resources?.getColor(R.color.doubleColor6) // Replace with the selected color
            ShowSaveColor(selectedColor!!, selectedColor1!!, selectedColor2!!)
        }
        binding.ibGradient4.setOnClickListener {
            val selectedColor =
                context?.resources?.getColor(R.color.doubleColor7) // Replace with the selected color
            val selectedColor1 =
                context?.resources?.getColor(R.color.doubleColor8) // Replace with the selected color
            val selectedColor2 =
                context?.resources?.getColor(R.color.doubleColor8) // Replace with the selected color
            // Save the selected color in preferences
            ShowSaveColor(selectedColor!!, selectedColor1!!, selectedColor2!!)
        }
        binding.ibGradient5.setOnClickListener {
            val selectedColor =
                context?.resources?.getColor(R.color.doubleColor9) // Replace with the selected color
            val selectedColor1 =
                context?.resources?.getColor(R.color.doubleColor10) // Replace with the selected color
            val selectedColor2 =
                context?.resources?.getColor(R.color.doubleColor9) // Replace with the selected color
            ShowSaveColor(selectedColor!!, selectedColor1!!, selectedColor2!!)
        }

        binding.ibGradient6.setOnClickListener {
            val selectedColor =
                context?.resources?.getColor(R.color.doubleColor11) // Replace with the selected color
            val selectedColor1 =
                context?.resources?.getColor(R.color.doubleColor12) // Replace with the selected color
            val selectedColor2 =
                context?.resources?.getColor(R.color.doubleColor11) // Replace with the selected color
            // Save the selected color in preferences
            ShowSaveColor(selectedColor!!, selectedColor1!!, selectedColor2!!)
        }

        binding.ibGradient7.setOnClickListener {
            val selectedColor =
                context?.resources?.getColor(R.color.doubleColor13) // Replace with the selected color
            val selectedColor1 =
                context?.resources?.getColor(R.color.doubleColor14) // Replace with the selected color
            val selectedColor2 =
                context?.resources?.getColor(R.color.doubleColor13) // Replace with the selected color
            ShowSaveColor(selectedColor!!, selectedColor1!!, selectedColor2!!)
        }
        binding.ibGradient8.setOnClickListener {
            val selectedColor =
                context?.resources?.getColor(R.color.doubleColor15) // Replace with the selected color
            val selectedColor1 =
                context?.resources?.getColor(R.color.doubleColor16) // Replace with the selected color
            val selectedColor2 =
                context?.resources?.getColor(R.color.doubleColor15) // Replace with the selected color
            ShowSaveColor(selectedColor!!, selectedColor1!!, selectedColor2!!)
        }
        binding.ibGradient9.setOnClickListener {
            val selectedColor =
                context?.resources?.getColor(R.color.doubleColor17) // Replace with the selected color
            val selectedColor1 =
                context?.resources?.getColor(R.color.doubleColor18) // Replace with the selected color
            val selectedColor2 =
                context?.resources?.getColor(R.color.doubleColor17) // Replace with the selected color
            ShowSaveColor(selectedColor!!, selectedColor1!!, selectedColor2!!)
        }
        binding.ibGradient10.setOnClickListener {

            val selectedColor =
                context?.resources?.getColor(R.color.doubleColor19) // Replace with the selected color
            val selectedColor1 =
                context?.resources?.getColor(R.color.doubleColor20) // Replace with the selected color
            val selectedColor2 =
                context?.resources?.getColor(R.color.doubleColor19) // Replace with the selected color
            // Save the selected color in preferences
            ShowSaveColor(selectedColor!!, selectedColor1!!, selectedColor2!!)


        }

//how do i make a class of MessageBroadcastReceiver which detect the sms received

    }

    fun ShowSaveColor(selectedColor: Int, selectedColor1: Int, selectedColor2: Int) {
        if (selectedColor != null) {
            MySharedPrefs(requireContext()).setIntPref(CommonKeys.SelectedColor, selectedColor)
            if (selectedColor1 != null) {
                MySharedPrefs(requireContext()).setIntPref(
                    CommonKeys.SelectedColor2,
                    selectedColor1
                )
            }
            if (selectedColor2 != null) {
                MySharedPrefs(requireContext()).setIntPref(
                    CommonKeys.SelectedColor3,
                    selectedColor2
                )
            }
        }

        // Broadcast an intent to notify DashboardActivity
        val intent = Intent("colorChanged1")
        intent.putExtra("color1", selectedColor)
        intent.putExtra("color2", selectedColor1)
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
    }

}