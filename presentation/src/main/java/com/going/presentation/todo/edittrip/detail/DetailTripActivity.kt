package com.going.presentation.todo.edittrip.detail

import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.ActivityDetailTripBinding
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.TRIP_ID
import com.going.presentation.todo.edittrip.QuitTripDialogFragment
import com.going.presentation.todo.edittrip.editinfo.EditTripInfoActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import com.going.ui.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailTripActivity :
    BaseActivity<ActivityDetailTripBinding>(R.layout.activity_detail_trip) {
    private val viewModel by viewModels<DetailTripViewModel>()
    private var quitDialog: QuitTripDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBindingViewModel()
        getIntentData()
        observeTripinfoState()
        observePatchQuitState()
        initEditBtnClickListener()
        initQuitBtnClickListener()
        initBackBtnClickListener()
    }

    private fun initBindingViewModel() {
        binding.vm = viewModel
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
                        tvEditTripName.text = viewModel.title
                        tvEditTripInfoStartDate.text = viewModel.startDate
                        tvEditTripInfoEndDate.text = viewModel.endDate
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

    private fun observePatchQuitState() {
        viewModel.quittripState.flowWithLifecycle(lifecycle).onEach { result ->
            if (result) {
                toast(getString(R.string.quit_trip_toast_success))
                setResult(Activity.RESULT_OK)
                finish()
                return@onEach
            }
            toast(getString(R.string.quit_trip_toast_failure))
        }.launchIn(lifecycleScope)
    }

    private fun initQuitBtnClickListener() {
        binding.btnEditTripQuit.setOnSingleClickListener {
            showQuitDialog()
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

    private fun showQuitDialog() {
        quitDialog = QuitTripDialogFragment()
        quitDialog?.show(supportFragmentManager, quitDialog?.tag)
    }

    private fun initBackBtnClickListener() {
        binding.btnEditTripInfoBack.setOnSingleClickListener {
            finish()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (quitDialog?.isAdded == true) quitDialog?.dismiss()
    }

}