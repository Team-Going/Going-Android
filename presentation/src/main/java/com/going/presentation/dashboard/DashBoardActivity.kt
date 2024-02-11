package com.going.presentation.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTripDashBoardBinding
import com.going.presentation.entertrip.StartTripSplashActivity
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.TRIP_ID
import com.going.presentation.setting.SettingActivity
import com.going.presentation.todo.TodoActivity
import com.going.presentation.util.initOnBackPressedListener
import com.going.presentation.util.navigateToScreen
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashBoardActivity :
    BaseActivity<ActivityTripDashBoardBinding>(R.layout.activity_trip_dash_board) {

    private val tabTextList = listOf(TAB_ONGOING, TAB_COMPLETED)

    private val viewModel by viewModels<DashBoardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkIsFirstEntered()
        setTabLayout()
        setViewPager()
        setTravelerName()
        initSettingBtnClickListener()
        initCreateTripBtnClickListener()
        initOnBackPressedListener()
    }

    private fun checkIsFirstEntered() {
        if (intent.getBooleanExtra(IS_FIRST_ENTERED, false)) {
            val tripId = intent.getLongExtra(TRIP_ID, 0)
            TodoActivity.createIntent(
                this,
                tripId
            ).apply { startActivity(this) }
        }
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

    private fun initSettingBtnClickListener() {
        binding.btnDashboardSetting.setOnSingleClickListener {
            navigateToScreen<SettingActivity>(isFinish = false)
        }
    }

    private fun initCreateTripBtnClickListener() {
        binding.btnDashboardCreateTrip.setOnSingleClickListener {
            navigateToScreen<StartTripSplashActivity>(isFinish = false)
        }
    }

    companion object {
        const val TAB_ONGOING = "진행 중인 여행"
        const val TAB_COMPLETED = "지나간 여행"
        const val IS_FIRST_ENTERED = "isFirstEntered"

        @JvmStatic
        fun createIntent(
            context: Context,
            tripId: Long
        ): Intent = Intent(context, DashBoardActivity::class.java).apply {
            putExtra(TRIP_ID, tripId)
            putExtra(IS_FIRST_ENTERED, true)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

    }
}
