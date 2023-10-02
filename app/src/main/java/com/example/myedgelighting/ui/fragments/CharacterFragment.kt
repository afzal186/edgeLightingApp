package com.example.myedgelighting.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.myedgelighting.R
import com.example.myedgelighting.custom_edge_view.EdgeOverlayView
import com.example.myedgelighting.databinding.ActivityEdgeLightingScreenBinding
import com.example.myedgelighting.databinding.FragmentCharacterBinding
import com.example.myedgelighting.databinding.FragmentEmojisBinding
import com.example.myedgelighting.utils.CommonKeys
import com.example.myedgelighting.utils.MySharedPrefs

//class CharacterFragment : Fragment() {
//    private lateinit var binding: FragmentCharacterBinding
//    private lateinit var edgeOverlayView: EdgeOverlayView
//    private var lastSelectedButtonId: Int = -1
//    private var lastSelectedIv: ImageView? = null
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentCharacterBinding.inflate(layoutInflater)
//        return binding.root
//
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        edgeOverlayView = EdgeOverlayView(this.requireContext(), null)
//        lastSelectedButtonId = MySharedPrefs(requireContext()).getIntPref(CommonKeys.LAST_SELECTED_ALPHABET_ID, -1)
//        var lastSelectedIv = binding.ivSelected1
//        if (lastSelectedButtonId != -1) {
//            val lastSelectedIv = binding.root.findViewById<ImageView>(lastSelectedButtonId)
//            //when you said the root then it removes my ibgradient
//            lastSelectedIv.visibility = View.VISIBLE
//        }
//        binding.ivSelected1.visibility = View.INVISIBLE
//        binding.ivSelected2.visibility = View.INVISIBLE
//        binding.ivSelected3.visibility = View.INVISIBLE
//        binding.ivSelected4.visibility = View.INVISIBLE
//        binding.ivSelected5.visibility = View.INVISIBLE
//        binding.ivSelected6.visibility = View.INVISIBLE
//        binding.ivSelected7.visibility = View.INVISIBLE
//        binding.ivSelected8.visibility = View.INVISIBLE
//        binding.ivSelected9.visibility = View.INVISIBLE
//        binding.ivSelected10.visibility = View.INVISIBLE
//        lastSelectedIv?.visibility = View.VISIBLE
//
//
//
//        binding.ibGradient1.setOnClickListener {
//              edgeOverlayView.switchDisplayMode(1)
//                        lastSelectedIv.visibility = View.INVISIBLE
//                        lastSelectedIv = binding.ivSelected1
//                        lastSelectedIv.visibility = View.VISIBLE
//                        updateSelectionAndVisibility(binding.ivSelected1)
//                        edgeOverlayView.setCustomString("⚡")
//                        MySharedPrefs(requireContext()).setIntPref(
//                            CommonKeys.LAST_SELECTED_ALPHABET_ID,
//                            R.id.ivSelected1
//                        )
//                        MySharedPrefs(this.requireContext()).setStringPref(
//                            CommonKeys.CUSTOM_STRING,
//                            "⚡"
//                        )
//
//        }
//
//        binding.ibGradient2.setOnClickListener {
//
//            edgeOverlayView.switchDisplayMode(1)
//                        lastSelectedIv!!.visibility = View.INVISIBLE
//                        lastSelectedIv = binding.ivSelected2
//                        lastSelectedIv.visibility = View.VISIBLE
//                        updateSelectionAndVisibility(binding.ivSelected1)
//                        edgeOverlayView.setCustomString("🍄")
//                        MySharedPrefs(requireContext()).setIntPref(
//                            CommonKeys.LAST_SELECTED_ALPHABET_ID,
//                            R.id.ivSelected2
//                        )
//                        MySharedPrefs(this.requireContext()).setStringPref(
//                            CommonKeys.CUSTOM_STRING,
//                            "🍄"
//                        )
//
//        }
//        binding.ibGradient3.setOnClickListener {
//
//             edgeOverlayView.switchDisplayMode(1)
//                        lastSelectedIv!!.visibility = View.INVISIBLE
//                        lastSelectedIv = binding.ivSelected3
//                        lastSelectedIv.visibility = View.VISIBLE
//                        updateSelectionAndVisibility(binding.ivSelected1)
//                        edgeOverlayView.setCustomString("🙅")
//                        MySharedPrefs(requireContext()).setIntPref(
//                            CommonKeys.LAST_SELECTED_ALPHABET_ID,
//                            R.id.ivSelected3
//                        )
//                        MySharedPrefs(this.requireContext()).setStringPref(
//                            CommonKeys.CUSTOM_STRING,
//                            "🙅"
//                        )
//
//        }
//
//        binding.ibGradient4.setOnClickListener {
//              edgeOverlayView.switchDisplayMode(1)
//                                    lastSelectedIv!!.visibility = View.INVISIBLE
//                                    lastSelectedIv = binding.ivSelected4
//                                    lastSelectedIv.visibility = View.VISIBLE
//                                    updateSelectionAndVisibility(binding.ivSelected1)
//                                    edgeOverlayView.setCustomString("👒")
//                                    MySharedPrefs(requireContext()).setIntPref(
//                                        CommonKeys.LAST_SELECTED_ALPHABET_ID,
//                                        R.id.ivSelected4
//                                    )
//                                    MySharedPrefs(this.requireContext()).setStringPref(
//                                        CommonKeys.CUSTOM_STRING,
//                                        "👒"
//                                    )
//
//        }
//
//        binding.ibGradient5.setOnClickListener {
//            edgeOverlayView.switchDisplayMode(1)
//            lastSelectedIv!!.visibility = View.INVISIBLE
//            lastSelectedIv = binding.ivSelected5
//            lastSelectedIv.visibility = View.VISIBLE
//            updateSelectionAndVisibility(binding.ivSelected1)
//            edgeOverlayView.setCustomString("✿")
//            MySharedPrefs(requireContext()).setIntPref(
//                CommonKeys.LAST_SELECTED_ALPHABET_ID,
//                R.id.ivSelected5
//            )
//            MySharedPrefs(this.requireContext()).setStringPref(
//                CommonKeys.CUSTOM_STRING,
//                "✿"
//            )
//
//        }
//
//        binding.ibGradient6.setOnClickListener {
//             edgeOverlayView.switchDisplayMode(1)
//                        lastSelectedIv!!.visibility = View.INVISIBLE
//                        lastSelectedIv = binding.ivSelected6
//                        lastSelectedIv.visibility = View.VISIBLE
//                        updateSelectionAndVisibility(binding.ivSelected1)
//                        edgeOverlayView.setCustomString("♡")
//                        MySharedPrefs(requireContext()).setIntPref(
//                            CommonKeys.LAST_SELECTED_ALPHABET_ID,
//                            R.id.ivSelected6
//                        )
//                        MySharedPrefs(this.requireContext()).setStringPref(
//                            CommonKeys.CUSTOM_STRING,
//                            "♡"
//                        )
//        }
//
//        binding.ibGradient7.setOnClickListener {
//            edgeOverlayView.switchDisplayMode(1)
//            lastSelectedIv!!.visibility = View.INVISIBLE
//            lastSelectedIv = binding.ivSelected7
//            lastSelectedIv.visibility = View.VISIBLE
//            updateSelectionAndVisibility(binding.ivSelected1)
//            edgeOverlayView.setCustomString("☁")
//            MySharedPrefs(requireContext()).setIntPref(
//                CommonKeys.LAST_SELECTED_ALPHABET_ID,
//                R.id.ivSelected7
//            )
//            MySharedPrefs(this.requireContext()).setStringPref(
//                CommonKeys.CUSTOM_STRING,
//                "☁"
//            )
//        }
//
//        binding.ibGradient8.setOnClickListener {
//            edgeOverlayView.switchDisplayMode(1)
//            lastSelectedIv!!.visibility = View.INVISIBLE
//            lastSelectedIv = binding.ivSelected8
//            lastSelectedIv.visibility = View.VISIBLE
//            updateSelectionAndVisibility(binding.ivSelected1)
//            edgeOverlayView.setCustomString("✩")
//            MySharedPrefs(requireContext()).setIntPref(
//                CommonKeys.LAST_SELECTED_ALPHABET_ID,
//                R.id.ivSelected8
//            )
//            MySharedPrefs(this.requireContext()).setStringPref(
//                CommonKeys.CUSTOM_STRING,
//                "✩"
//            )
//        }
//
//        binding.ibGradient9.setOnClickListener {
//            edgeOverlayView.switchDisplayMode(1)
//            lastSelectedIv!!.visibility = View.INVISIBLE
//            lastSelectedIv = binding.ivSelected9
//            lastSelectedIv.visibility = View.VISIBLE
//            updateSelectionAndVisibility(binding.ivSelected1)
//            edgeOverlayView.setCustomString("\uD83D\uDC2C")
//            MySharedPrefs(requireContext()).setIntPref(
//                CommonKeys.LAST_SELECTED_ALPHABET_ID,
//                R.id.ivSelected9
//            )
//            MySharedPrefs(this.requireContext()).setStringPref(
//                CommonKeys.CUSTOM_STRING,
//                "\uD83D\uDC2C"
//            )
//
//        }
//
//        binding.ibGradient10.setOnClickListener {
//            edgeOverlayView.switchDisplayMode(1)
//            lastSelectedIv!!.visibility = View.INVISIBLE
//            lastSelectedIv = binding.ivSelected10
//            lastSelectedIv.visibility = View.VISIBLE
//            updateSelectionAndVisibility(binding.ivSelected1)
//            edgeOverlayView.setCustomString("〰")
//            MySharedPrefs(requireContext()).setIntPref(
//                CommonKeys.LAST_SELECTED_ALPHABET_ID,
//                R.id.ivSelected10
//            )
//            MySharedPrefs(this.requireContext()).setStringPref(
//                CommonKeys.CUSTOM_STRING,
//                "〰"
//            )
//        }
//
//    }
//
//    fun updateSelectionAndVisibility(ivSelected: ImageView) {
//
//        lastSelectedIv?.visibility = View.INVISIBLE
//        lastSelectedIv = ivSelected
//        lastSelectedIv?.visibility = View.VISIBLE
//
//    }
//
//}


class CharacterFragment : Fragment() {
    private lateinit var binding: FragmentCharacterBinding

    private var lastSelectedButtonId: Int = -1
    private var lastSelectedIv: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root

    }


    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // edgeOverlayView = EdgeOverlayView(this.requireContext(), null)
        lastSelectedButtonId =
            MySharedPrefs(requireContext()).getIntPref(CommonKeys.LAST_SELECTED_ALPHABET_ID, -1)
        var lastSelectedIv = binding.ivSelected1
        if (lastSelectedButtonId != -1) {
            val lastSelectedIv = binding.root.findViewById<ImageView>(lastSelectedButtonId)
            //when you said the root then it removes my ibgradient
            lastSelectedIv.visibility = View.VISIBLE
        }
        binding.ivSelected1.visibility = View.INVISIBLE
        binding.ivSelected2.visibility = View.INVISIBLE
        binding.ivSelected3.visibility = View.INVISIBLE
        binding.ivSelected4.visibility = View.INVISIBLE
        binding.ivSelected5.visibility = View.INVISIBLE
        binding.ivSelected6.visibility = View.INVISIBLE
        binding.ivSelected7.visibility = View.INVISIBLE
        binding.ivSelected8.visibility = View.INVISIBLE
        binding.ivSelected9.visibility = View.INVISIBLE
        binding.ivSelected10.visibility = View.INVISIBLE
        lastSelectedIv?.visibility = View.VISIBLE

        binding.ibGradient1.setOnClickListener {
            // edgeOverlayView.switchDisplayMode(1)
            lastSelectedIv.visibility = View.INVISIBLE
            lastSelectedIv = binding.ivSelected1
            lastSelectedIv.visibility = View.VISIBLE
            updateSelectionAndVisibility(binding.ivSelected1)
            // edgeOverlayView.setCustomString("⚡")
            MySharedPrefs(requireContext()).setIntPref(
                CommonKeys.LAST_SELECTED_ALPHABET_ID,
                R.id.ivSelected1
            )
            MySharedPrefs(this.requireContext()).setStringPref(
                CommonKeys.CUSTOM_STRING,
                "⚡"
            )

        }

        binding.ibGradient2.setOnClickListener {
            //edgeOverlayView.switchDisplayMode(1)
            lastSelectedIv.visibility = View.INVISIBLE
            lastSelectedIv = binding.ivSelected2
            lastSelectedIv.visibility = View.VISIBLE
            updateSelectionAndVisibility(binding.ivSelected2)
            // edgeOverlayView.setCustomString("\uD83C\uDF44")
            MySharedPrefs(requireContext()).setIntPref(
                CommonKeys.LAST_SELECTED_ALPHABET_ID,
                R.id.ivSelected2
            )
            MySharedPrefs(this.requireContext()).setStringPref(
                CommonKeys.CUSTOM_STRING,
                "\uD83C\uDF44"
            )

        }
        binding.ibGradient3.setOnClickListener {
            //  edgeOverlayView.switchDisplayMode(1)
            lastSelectedIv.visibility = View.INVISIBLE
            lastSelectedIv = binding.ivSelected3
            lastSelectedIv.visibility = View.VISIBLE
            updateSelectionAndVisibility(binding.ivSelected3)
            // edgeOverlayView.setCustomString("\uD83D\uDE45")
            MySharedPrefs(requireContext()).setIntPref(
                CommonKeys.LAST_SELECTED_ALPHABET_ID,
                R.id.ivSelected3
            )
            MySharedPrefs(this.requireContext()).setStringPref(
                CommonKeys.CUSTOM_STRING,
                "\uD83D\uDE45"
            )

        }

        binding.ibGradient4.setOnClickListener {
            //  edgeOverlayView.switchDisplayMode(1)
            lastSelectedIv.visibility = View.INVISIBLE
            lastSelectedIv = binding.ivSelected4
            lastSelectedIv.visibility = View.VISIBLE
            updateSelectionAndVisibility(binding.ivSelected4)
            // edgeOverlayView.setCustomString("\uD83D\uDC52")
            MySharedPrefs(requireContext()).setIntPref(
                CommonKeys.LAST_SELECTED_ALPHABET_ID,
                R.id.ivSelected4
            )
            MySharedPrefs(this.requireContext()).setStringPref(
                CommonKeys.CUSTOM_STRING,
                "\uD83D\uDC52"
            )

        }

        binding.ibGradient5.setOnClickListener {
            // edgeOverlayView.switchDisplayMode(1)
            lastSelectedIv.visibility = View.INVISIBLE
            lastSelectedIv = binding.ivSelected5
            lastSelectedIv.visibility = View.VISIBLE
            updateSelectionAndVisibility(binding.ivSelected5)
            // edgeOverlayView.setCustomString("✿")
            MySharedPrefs(requireContext()).setIntPref(
                CommonKeys.LAST_SELECTED_ALPHABET_ID,
                R.id.ivSelected5
            )
            MySharedPrefs(this.requireContext()).setStringPref(
                CommonKeys.CUSTOM_STRING,
                "✿"
            )

        }

        binding.ibGradient6.setOnClickListener {
            // edgeOverlayView.switchDisplayMode(1)
            lastSelectedIv.visibility = View.INVISIBLE
            lastSelectedIv = binding.ivSelected6
            lastSelectedIv.visibility = View.VISIBLE
            //  edgeOverlayView.setCustomString("♡")
            updateSelectionAndVisibility(binding.ivSelected6)
            MySharedPrefs(requireContext()).setIntPref(
                CommonKeys.LAST_SELECTED_ALPHABET_ID,
                R.id.ivSelected6
            )
            MySharedPrefs(this.requireContext()).setStringPref(
                CommonKeys.CUSTOM_STRING,
                "♡"
            )

        }

        binding.ibGradient7.setOnClickListener {
            //  edgeOverlayView.switchDisplayMode(1)
            lastSelectedIv.visibility = View.INVISIBLE
            lastSelectedIv = binding.ivSelected7
            lastSelectedIv.visibility = View.VISIBLE
            // edgeOverlayView.setCustomString("☁")
            updateSelectionAndVisibility(binding.ivSelected7)
            MySharedPrefs(requireContext()).setIntPref(
                CommonKeys.LAST_SELECTED_ALPHABET_ID,
                R.id.ivSelected7
            )
            MySharedPrefs(this.requireContext()).setStringPref(
                CommonKeys.CUSTOM_STRING,
                "☁"
            )

        }

        binding.ibGradient8.setOnClickListener {
            // edgeOverlayView.switchDisplayMode(1)
            // edgeOverlayView.setCustomString("✩")
            lastSelectedIv.visibility = View.INVISIBLE
            lastSelectedIv = binding.ivSelected8
            lastSelectedIv.visibility = View.VISIBLE
            updateSelectionAndVisibility(binding.ivSelected8)
            MySharedPrefs(requireContext()).setIntPref(
                CommonKeys.LAST_SELECTED_ALPHABET_ID,
                R.id.ivSelected8
            )
            MySharedPrefs(this.requireContext()).setStringPref(
                CommonKeys.CUSTOM_STRING,
                "✩"
            )

        }


        binding.ibGradient9.setOnClickListener {
            //  edgeOverlayView.switchDisplayMode(1)
            //  edgeOverlayView.setCustomString("\uD83D\uDC2C")
            lastSelectedIv.visibility = View.INVISIBLE
            lastSelectedIv = binding.ivSelected9
            lastSelectedIv.visibility = View.VISIBLE
            updateSelectionAndVisibility(binding.ivSelected9)
            MySharedPrefs(requireContext()).setIntPref(
                CommonKeys.LAST_SELECTED_ALPHABET_ID,
                R.id.ivSelected9
            )
            MySharedPrefs(this.requireContext()).setStringPref(
                CommonKeys.CUSTOM_STRING,
                "\uD83D\uDC2C"
            )

        }

        binding.ibGradient10.setOnClickListener {
            //edgeOverlayView.switchDisplayMode(1)
            // edgeOverlayView.setCustomString("〰")
            lastSelectedIv.visibility = View.INVISIBLE
            lastSelectedIv = binding.ivSelected10
            lastSelectedIv.visibility = View.VISIBLE
            updateSelectionAndVisibility(binding.ivSelected10)
            MySharedPrefs(requireContext()).setIntPref(
                CommonKeys.LAST_SELECTED_ALPHABET_ID,
                R.id.ivSelected10
            )
            MySharedPrefs(this.requireContext()).setStringPref(
                CommonKeys.CUSTOM_STRING,
                "〰"
            )

        }
    }


    fun updateSelectionAndVisibility(ivSelected: ImageView) {

        lastSelectedIv?.visibility = View.INVISIBLE
        lastSelectedIv = ivSelected
        lastSelectedIv?.visibility = View.VISIBLE

    }
}