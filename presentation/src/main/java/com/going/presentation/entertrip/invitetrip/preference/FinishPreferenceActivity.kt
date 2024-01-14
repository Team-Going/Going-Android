package com.going.presentation.entertrip.invitetrip.preference

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.PreferenceData
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.ActivityFinishPreferenceBinding
import com.going.presentation.entertrip.createtrip.choosedate.EnterTripActivity.Companion.TRIP_ID
import com.going.presentation.entertrip.invitetrip.finish.InviteFinishActivity
import com.going.presentation.onboarding.signin.SignInActivity
import com.going.presentation.preferencetag.PreferenceTagAdapter
import com.going.presentation.preferencetag.PreferenceTagDecoration
import com.going.ui.base.BaseActivity
import com.going.ui.extension.UiState
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FinishPreferenceActivity :
    BaseActivity<ActivityFinishPreferenceBinding>(R.layout.activity_finish_preference),
    PreferenceTagAdapter.OnPreferenceSelectedListener {

    private var backPressedTime: Long = 0

    private var _adapter: PreferenceTagAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by viewModels<FinishPreferenceViewModel>()

    private val preferenceAnswers = MutableList(5) { Int.MAX_VALUE }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()
        initItemDecoration()
        getTripId()
        initBackClickListener()
        initNextBtnClickListener()
        sendStyleInfo()
        observeFinishPreferenceState()
        initOnBackPressedListener()
    }

    private fun initAdapter() {
        _adapter = PreferenceTagAdapter(this, this)
        binding.rvPreferenceTag.adapter = adapter
        adapter.submitList(viewModel.preferenceTagList)
    }

    private fun initItemDecoration() {
        val itemDeco = PreferenceTagDecoration(this)
        binding.rvPreferenceTag.addItemDecoration(itemDeco)
    }

    private fun getTripId() {
        viewModel.tripId.value = intent.getLongExtra(TRIP_ID, -1L)
    }

    private fun initBackClickListener() {
        binding.btnPreferenceBack.setOnSingleClickListener {
            Intent(this, InviteFinishActivity::class.java).apply {
                startActivity(this)
            }
            finish()
        }
    }

    private fun isButtonValid() {
        val isValid = preferenceAnswers.all { it != Int.MAX_VALUE }

        if (isValid) {
            binding.btnPreferenceStart.isEnabled = isValid
            binding.btnPreferenceStart.setTextColor(
                ContextCompat.getColorStateList(this, R.color.white_000)
            )
        }
    }

    private fun observeFinishPreferenceState() {
        viewModel.finishInviteState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> navigateToDashBoard()

                is UiState.Failure -> {
                    toast(getString(R.string.server_error))
                }

                is UiState.Loading -> return@onEach

                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToDashBoard() {
        Intent(this, DashBoardActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(this)
        }
        finish()
    }

    private fun initNextBtnClickListener() {
        binding.btnPreferenceStart.setOnSingleClickListener {
            viewModel.checkStyleFromServer(viewModel.tripId.value ?: 0)
        }
    }

    private fun sendStyleInfo() {
        viewModel.styleA.value = preferenceAnswers[0]
        viewModel.styleB.value = preferenceAnswers[1]
        viewModel.styleC.value = preferenceAnswers[2]
        viewModel.styleD.value = preferenceAnswers[3]
        viewModel.styleE.value = preferenceAnswers[4]
    }

    override fun onPreferenceSelected(item: PreferenceData, checkList: Int) {
        preferenceAnswers[item.number.toInt() - 1] = checkList
        isButtonValid()
        sendStyleInfo()
    }

    private fun initOnBackPressedListener() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - backPressedTime >= SignInActivity.BACK_INTERVAL) {
                    backPressedTime = System.currentTimeMillis()
                    toast(getString(R.string.toast_back_pressed))
                } else {
                    finish()
                }
            }
        }
        this.onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }
}
