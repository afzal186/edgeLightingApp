package com.example.myedgelighting.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myedgelighting.ui.fragments.AlphabetsFragment
import com.example.myedgelighting.ui.fragments.CharacterFragment
import com.example.myedgelighting.ui.fragments.EmojisFragment

class BorderSelectionPager(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val COUNT = 3

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> EmojisFragment()
            1 -> CharacterFragment()
            2 -> AlphabetsFragment()
            else -> EmojisFragment()
        }
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Tab " + (position + 1)
    }
}