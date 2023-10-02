package com.example.myedgelighting.ui.activity.dashboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myedgelighting.ui.fragments.DoubleColorFragments
import com.example.myedgelighting.ui.fragments.SingleColorFragment
import com.example.myedgelighting.ui.fragments.TripleColorFragmets
import com.example.myedgelighting.utils.CommonKeys
import com.example.myedgelighting.utils.MySharedPrefs

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val COUNT = 3


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SingleColorFragment()
            1 -> DoubleColorFragments()
            2 -> TripleColorFragmets()
            else -> DoubleColorFragments()
        }
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Tab " + (position + 1)
    }
}
