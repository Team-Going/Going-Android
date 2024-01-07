package com.going.presentation.tripdashboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.going.presentation.tripdashboard.triplist.CompletedTripFragment
import com.going.presentation.tripdashboard.triplist.OngoingTripFragment

class DashBoardViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> OngoingTripFragment()
            else -> CompletedTripFragment()
        }
    }
}