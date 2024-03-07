package com.going.presentation.todo.editinfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.ActivityEditTripBinding
import com.going.presentation.todo.TodoActivity.Companion.EXTRA_TRIP_ID
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import com.going.ui.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class EditTripActivity :
    BaseActivity<ActivityEditTripBinding>(R.layout.activity_edit_trip) {
    private val viewModel by viewModels<EditTripViewModel>()
    private var quitDialog: TripQuitDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBindingViewModel()
        getIntentData()
        observeTripinfoState()
        initEditBtnClickListener()
        initQuitBtnClickListener()
        //showQuitDialog()
        initBackBtnClickListener()
    }

    private fun initBindingViewModel() {
        binding.viewModel = viewModel
    }

    private fun getIntentData() {
        val tripId = intent.getLongExtra(EXTRA_TRIP_ID, -1L)
        viewModel.getTripInfoFromServer(tripId)
    }

    private fun observeTripinfoState() {
        viewModel.tripInfoState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    with(binding) {
                        tvEditTripName.text = viewModel?.title
                        tvEditTripInfoStartDate.text = viewModel?.startDate
                        tvEditTripInfoEndDate.text = viewModel?.endDate
                    }
                }

                is UiState.Failure -> {
                    toast(getString(R.string.server_error))
                }

                is UiState.Loading -> return@onEach
                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun initQuitBtnClickListener() {
        binding.btnEditTripQuit.setOnSingleClickListener {
            //showQuitDialog()
        }
    }

    private fun initEditBtnClickListener() {
        binding.btnEditTripEdit.setOnSingleClickListener {
            Intent(this, EditTripInfoActivity::class.java).apply {
                //섭통 결과물
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(this)
            }
        }
    }

//    private fun showQuitDialog() {
//        quitDialog = TripQuitDialogFragment()
//        quitDialog?.show(supportFragmentManager, quitDialog?.tag)
//        Intent(this, DashBoardActivity::class.java).apply {
//            //정보 지워지게 구성
//            startActivity(this)
//        }
//    }

    private fun initBackBtnClickListener() {
        binding.btnEditTripInfoBack.setOnSingleClickListener {
            //어느 뷰로 가는지
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (quitDialog?.isAdded == true) quitDialog?.dismiss()
    }

    companion object {
        private const val EDIT_INFO_TRIP_ID = "TRIP_ID"

    }
}