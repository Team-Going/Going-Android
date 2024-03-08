package com.going.presentation.profile.participant

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.going.presentation.profile.trip.tripprofiletag.profiletag.TripProfileTagFragment

class ParticipantProfileViewPagerAdapter(activity: ParticipantProfileActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> ParticipantProfileCharacterFragment()
            else -> TripProfileTagFragment()
        }
}