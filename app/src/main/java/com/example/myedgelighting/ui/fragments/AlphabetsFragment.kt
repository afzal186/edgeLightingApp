package com.example.myedgelighting.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myedgelighting.databinding.FragmentAlphabetsBinding
import com.example.myedgelighting.utils.CommonKeys
import com.example.myedgelighting.utils.MySharedPrefs


class AlphabetsFragment : Fragment() {
    var tricksdata: java.util.ArrayList<TricksModel>? = null
    private lateinit var binding: FragmentAlphabetsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlphabetsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAlphabetsList()
    }

    private fun setAlphabetsList() {
        tricksdata = ArrayList<TricksModel>()
        tricksdata!!.add(TricksModel("A"))
        tricksdata!!.add(TricksModel("B"))
        tricksdata!!.add(TricksModel("C"))
        tricksdata!!.add(TricksModel("D"))
        tricksdata!!.add(TricksModel("E"))
        tricksdata!!.add(TricksModel("F"))
        tricksdata!!.add(TricksModel("G"))
        tricksdata!!.add(TricksModel("H"))
        tricksdata!!.add(TricksModel("I"))
        tricksdata!!.add(TricksModel("J"))
        tricksdata!!.add(TricksModel("K"))
        tricksdata!!.add(TricksModel("L"))
        tricksdata!!.add(TricksModel("M"))
        tricksdata!!.add(TricksModel("N"))
        tricksdata!!.add(TricksModel("O"))
        tricksdata!!.add(TricksModel("P"))
        tricksdata!!.add(TricksModel("Q"))
        tricksdata!!.add(TricksModel("R"))
        tricksdata!!.add(TricksModel("S"))
        tricksdata!!.add(TricksModel("T"))
        tricksdata!!.add(TricksModel("U"))
        tricksdata!!.add(TricksModel("V"))
        tricksdata!!.add(TricksModel("W"))
        tricksdata!!.add(TricksModel("X"))
        tricksdata!!.add(TricksModel("Y"))
        tricksdata!!.add(TricksModel("Z"))

        val adapter = AlphabetAdapter(requireContext(), tricksdata!!)
        binding.rvCharcters.adapter = adapter
        val layoutManager = GridLayoutManager(requireContext(), 5)
        binding.rvCharcters.layoutManager = layoutManager

        val lastSelectedPosition =
            MySharedPrefs(requireContext()).getIntPref(CommonKeys.LAST_SELECTED_POSITION, -1)
        if (lastSelectedPosition != -1) {
            adapter.selectedPosition = lastSelectedPosition
            adapter.notifyDataSetChanged()
        }

    }


}

class TricksModel(val value: String)
