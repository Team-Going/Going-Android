package com.going.presentation.dashboard

import android.os.Bundle
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTripDashBoardBinding
import com.going.ui.base.BaseActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashBoardActivity :
    BaseActivity<ActivityTripDashBoardBinding>(R.layout.activity_trip_dash_board) {

    private val tabTextList = listOf(TAB_ONGOING, TAB_COMPLETED)

    private val viewModel by viewModels<DashBoardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTabLayout()
        setViewPager()
        setTravelerName()

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
        binding.vpDashboard.isUserInputEnabled = false
        TabLayoutMediator(binding.tabDashboard, binding.vpDashboard) { tab, pos ->
            tab.text = tabTextList[pos]
        }.attach()
    }

    private fun setTravelerName() {
        val progress = DashBoardViewModel.COMPLETED
        viewModel.getTravelerNameFromServer(progress)
        viewModel.name.observe(this) { travelerName ->
            binding.tvDashboardTitle.text = getString(R.string.dashboard_tv_title, travelerName)
        }
    }

    companion object {
        const val TAB_ONGOING = "진행중인 여행"
        const val TAB_COMPLETED = "지나간 여행"
    }

}