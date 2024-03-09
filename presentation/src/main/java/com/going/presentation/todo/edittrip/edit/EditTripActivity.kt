package com.going.presentation.todo.edittrip.edit

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.ActivityEditTripBinding
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.TRIP_ID
import com.going.presentation.todo.edittrip.TripQuitDialogFragment
import com.going.presentation.todo.edittrip.info.EditTripInfoActivity
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
        viewModel.tripId = intent.getLongExtra(TRIP_ID, -1L)
        viewModel.getTripInfoFromServer(viewModel.tripId)
    }

    private fun observeTripinfoState() {
        viewModel.tripInfoState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    with(binding) {
                        tvEditTripName.text = viewModel?.title
                        tvEditTripInfoStartDate.text = viewModel?.startDate
                        tvEditTripInfoEndDate.text = viewModel?.endDate
                        viewModel?.gettitleLength()
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
            EditTripInfoActivity.createIntent(
                this,
                viewModel.tripId,
                viewModel.title,
                viewModel.startDate,
                viewModel.endDate
            ).apply { startActivity(this) }

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

}