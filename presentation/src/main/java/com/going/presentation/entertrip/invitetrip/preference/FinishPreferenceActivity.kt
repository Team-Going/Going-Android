package com.going.presentation.entertrip.invitetrip.preference

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.ActivityFinishPreferenceBinding
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.TRIP_ID
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripViewModel.Companion.ERROR_ALREADY_EXIST
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripViewModel.Companion.ERROR_OVER_SIX
import com.going.presentation.entertrip.preferencetag.PreferenceTagAdapter
import com.going.ui.base.BaseActivity
import com.going.ui.extension.colorOf
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import com.going.ui.state.UiState
import com.going.ui.util.RVItemFirstLastDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FinishPreferenceActivity :
    BaseActivity<ActivityFinishPreferenceBinding>(R.layout.activity_finish_preference) {

    private var _adapter: PreferenceTagAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by viewModels<FinishPreferenceViewModel>()

    private val preferenceAnswers = MutableList(5) { Int.MAX_VALUE }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapterWithClickListener()
        initItemDecoration()
        getTripId()
        initNextBtnClickListener()
        initBackBtnClickListener()
        sendStyleInfo()
        observeFinishPreferenceState()
    }

    private fun initAdapterWithClickListener() {
        _adapter = PreferenceTagAdapter(
            this
        ) { item, checkedIndex ->
            preferenceAnswers[item.number.toInt() - 1] = checkedIndex
            isButtonValid()
            sendStyleInfo()
        }
        binding.rvPreferenceTag.adapter = adapter
        adapter.submitList(viewModel.preferenceTagList)
    }

    private fun initItemDecoration() {
        val itemDeco = RVItemFirstLastDecoration(50, 30)
        binding.rvPreferenceTag.addItemDecoration(itemDeco)
    }

    private fun getTripId() {
        viewModel.tripId.value = intent.getLongExtra(TRIP_ID, -1L)
    }

    private fun isButtonValid() {
        val isValid = preferenceAnswers.all { it != Int.MAX_VALUE }

        if (isValid) {
            binding.btnPreferenceStart.isEnabled = true
            binding.btnPreferenceStart.setTextColor(
                colorOf(R.color.white_000)
            )
        }
    }

    private fun observeFinishPreferenceState() {
        viewModel.finishInviteState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> navigateToDashBoard(state.data.tripId)

                is UiState.Failure -> {
                    when (state.msg) {
                        ERROR_ALREADY_EXIST -> toast(getString(R.string.enter_trip_my_code_toast))

                        ERROR_OVER_SIX -> toast(getString(R.string.enter_trip_invite_code_over_toast))

                        else -> toast(getString(R.string.server_error))
                    }
                }

                is UiState.Loading -> return@onEach

                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToDashBoard(tripId: Long) {
        DashBoardActivity.createIntent(
            this,
            tripId
        ).apply { startActivity(this) }
        finish()
    }

    private fun initNextBtnClickListener() {
        binding.btnPreferenceStart.setOnSingleClickListener {
            viewModel.checkStyleFromServer(viewModel.tripId.value ?: 0)
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnPreferenceBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun sendStyleInfo() {
        viewModel.styleA.value = preferenceAnswers[0]
        viewModel.styleB.value = preferenceAnswers[1]
        viewModel.styleC.value = preferenceAnswers[2]
        viewModel.styleD.value = preferenceAnswers[3]
        viewModel.styleE.value = preferenceAnswers[4]
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }

    companion object {
        @JvmStatic
        fun createIntent(
            context: Context,
            tripId: Long
        ): Intent = Intent(context, FinishPreferenceActivity::class.java).apply {
            putExtra(TRIP_ID, tripId)
        }
    }
}
