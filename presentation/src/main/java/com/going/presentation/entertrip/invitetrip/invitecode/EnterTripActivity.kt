package com.going.presentation.entertrip.invitetrip.invitecode

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.CodeState
import com.going.presentation.R
import com.going.presentation.databinding.ActivityEnterTripBinding
import com.going.presentation.entertrip.invitetrip.finish.InviteFinishActivity
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripViewModel.Companion.ERROR_NO_EXIST
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripViewModel.Companion.ERROR_OVER_SIX
import com.going.ui.base.BaseActivity
import com.going.ui.state.UiState
import com.going.ui.extension.hideKeyboard
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class EnterTripActivity : BaseActivity<ActivityEnterTripBinding>(R.layout.activity_enter_trip) {

    private val viewModel by viewModels<EnterTripViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnClickListener()
        initBindingViewModel()
        observeIsCodeAvailable()
        initNextBtnClickListener()
        observeEnterTripState()
        binding.etEnterTripName.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (binding.btnEnterTripNext.isEnabled) {
                    viewModel.checkInviteCodeFromServer()
                }
                hideKeyboard(v)
            }
            true
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnEnterBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun initBindingViewModel() {
        binding.viewModel = viewModel
    }

    private fun observeIsCodeAvailable() {
        viewModel.isCodeAvailable.observe(this) { state ->
            setColors(
                false,
                viewModel.codeLength.value ?: 0,
                binding.tvCodeCounter,
            ) { background ->
                binding.etEnterTripName.background = ResourcesCompat.getDrawable(
                    this.resources,
                    background,
                    theme,
                )
            }
        }
    }

    private fun setColors(
        hasFocus: Boolean,
        length: Int,
        counter: TextView,
        setBackground: (Int) -> Unit,
    ) {
        val (color, background) = when {
            viewModel.isCodeAvailable.value != CodeState.Blank && hasFocus -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
            length == 0 -> R.color.gray_200 to R.drawable.shape_rect_4_gray200_line
            (viewModel.isCodeAvailable.value == CodeState.Blank || viewModel.isCodeAvailable.value == CodeState.Invalid) && counter == binding.tvCodeCounter -> R.color.red_500 to R.drawable.shape_rect_4_red500_line
            else -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
        }
        setCounterColor(counter, color)
        setBackground(background)
    }

    private fun setCounterColor(counter: TextView, color: Int) {
        counter.setTextColor(getColor(color))
    }

    private fun observeEnterTripState() {
        viewModel.tripState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    Intent(this, InviteFinishActivity::class.java).apply {
                        putExtra(TRIP_ID, state.data.tripId)
                        putExtra(TITLE, state.data.title)
                        putExtra(START, state.data.startDate)
                        putExtra(END, state.data.endDate)
                        putExtra(DAY, state.data.day)
                        startActivity(this)
                    }
                }

                is UiState.Failure -> {
                    when (state.msg) {
                        ERROR_NO_EXIST -> toast(getString(R.string.enter_trip_invite_code_exist_toast))

                        ERROR_OVER_SIX -> toast(getString(R.string.enter_trip_invite_code_over_toast))

                        else -> toast(getString(R.string.server_error))
                    }
                }

                is UiState.Loading -> return@onEach

                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun initNextBtnClickListener() {
        binding.btnEnterTripNext.setOnSingleClickListener {
            viewModel.checkInviteCodeFromServer()
        }
    }

    companion object {
        const val TRIP_ID = "tripId"
        const val TITLE = "title"
        const val START = "start"
        const val END = "end"
        const val INVITE_CODE = "code"
        const val DAY = "day"
    }
}
