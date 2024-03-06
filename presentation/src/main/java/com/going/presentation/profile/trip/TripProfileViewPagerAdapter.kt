package com.going.presentation.profile.trip

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TripProfileViewPagerAdapter(activity: TripProfileActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> TripProfileCharacterFragment()
            else -> TripProfileTagFragment()
        }
}