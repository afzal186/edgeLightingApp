package com.example.myedgelighting.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.myedgelighting.R
import com.example.myedgelighting.custom_edge_view.EdgeOverlayView
import com.example.myedgelighting.databinding.FragmentTripleColorFragmetsBinding
import com.example.myedgelighting.utils.CommonKeys
import com.example.myedgelighting.utils.MySharedPrefs


class TripleColorFragmets : Fragment() {
    private lateinit var binding: FragmentTripleColorFragmetsBinding
    private lateinit var edgeOverlayView: EdgeOverlayView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTripleColorFragmetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edgeOverlayView = EdgeOverlayView(this.requireContext(), null)


        binding.ibGradient1.setOnClickListener {
            val selectedColor =
                context?.resources?.getColor(R.color.doubleColor1) // Replace with the selected color
            val selectedColor1 =
                context?.resources?.getColor(R.color.doubleColor2) // Replace with the selected color
            val selectedColor2 =
                context?.resources?.getColor(R.color.doubleColor1) // Replace with the selected color
            // Save the selected color in preferences
            ShowSaveColor(selectedColor!!, selectedColor1!!, selectedColor2!!)
        }

        binding.ibGradient2.setOnClickListener {
            val selectedColor =
                context?.resources?.getColor(R.color.doubleColor4) // Replace with the selected color
            val selectedColor1 =
                context?.resources?.getColor(R.color.doubleColor3) // Replace with the selected color
            val selectedColor2 =
                context?.resources?.getColor(R.color.doubleColor4) // Replace with the selected color
            // Save the selected color in preferences
            ShowSaveColor(selectedColor!!, selectedColor1!!, selectedColor2!!)
        }

        binding.ibGradient3.setOnClickListener {
            val selectedColor =
                context?.resources?.getColor(R.color.doubleColor5) // Replace with the selected color
            val selectedColor1 =
                context?.resources?.getColor(R.color.doubleColor6) // Replace with the selected color
            val selectedColor2 =
                context?.resources?.getColor(R.color.doubleColor5) // Replace with the selected color
            ShowSaveColor(selectedColor!!, selectedColor1!!, selectedColor2!!)
        }
        binding.ibGradient4.setOnClickListener {
            val selectedColor =
                context?.resources?.getColor(R.color.doubleColor7) // Replace with the selected color
            val selectedColor1 =
                context?.resources?.getColor(R.color.doubleColor8) // Replace with the selected color
            val selectedColor2 =
                context?.resources?.getColor(R.color.doubleColor7) // Replace with the selected color
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
//        edgeOverlayView.updateColorsForTripleColorMode()
//        edgeOverlayView.switchDisplayColorMode(3)

        // Broadcast an intent to notify DashboardActivity
        val intent = Intent("colorChanged1")
        intent.putExtra("color1", selectedColor)
        intent.putExtra("color2", selectedColor1)
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
    }


}