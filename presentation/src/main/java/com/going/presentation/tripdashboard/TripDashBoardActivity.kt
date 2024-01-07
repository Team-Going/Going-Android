package com.going.presentation.tripdashboard

import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTripDashBoardBinding
import com.going.presentation.tripdashboard.triplist.OngoingTripFragment
import com.going.ui.base.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator

class TripDashBoardActivity :
    BaseActivity<ActivityTripDashBoardBinding>(R.layout.activity_trip_dash_board) {

    private val tabTextList = listOf(TAB_ONGOING, TAB_COMPLETED)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTabLayout()
        setViewPager()

    }

    private fun setTabLayout() {
        binding.tabDashboard.apply {
            for (tabName in tabTextList) {
                val tab = this.newTab()
                tab.text = tabName
                this.addTab(tab)
            }
        }
    }

    private fun setViewPager() {
        binding.vpDashboard.adapter = DashBoardViewPagerAdapter(this)
        TabLayoutMediator(binding.tabDashboard, binding.vpDashboard) { tab, pos ->
            tab.text = tabTextList[pos]
        }.attach()
    }

    companion object {
        const val TAB_ONGOING = "진행중인 여행"
        const val TAB_COMPLETED = "완료된 여행"
    }

}