package com.going.presentation.dashboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.going.presentation.dashboard.triplist.completed.CompletedTripFragment
import com.going.presentation.dashboard.triplist.ongoing.OngoingTripFragment

class DashBoardViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OngoingTripFragment()
            else -> CompletedTripFragment()
        }
    }
}