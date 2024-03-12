package com.going.presentation.todo.ourtodo.checkfriends

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.response.CheckFriendsModel
import com.going.domain.entity.response.TripParticipantModel
import com.going.presentation.R
import com.going.presentation.databinding.ActivityCheckFriendsBinding
import com.going.presentation.profile.participant.ParticipantProfileActivity
import com.going.presentation.todo.TodoActivity.Companion.EXTRA_TRIP_ID
import com.going.ui.base.BaseActivity
import com.going.ui.extension.colorOf
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import com.going.ui.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CheckFriendsActivity :
    BaseActivity<ActivityCheckFriendsBinding>(R.layout.activity_check_friends) {

    private var _adapter: CheckFriendsAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by viewModels<CheckFriendsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackClickListener()
        initAdapterWithClickListener()
        observeCheckFriendsListState()
    }

    override fun onResume() {
        super.onResume()

        getTripId()
    }

    private fun initBackClickListener() {
        binding.btnCheckFriendsBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun initAdapterWithClickListener() {
        _adapter = CheckFriendsAdapter { participantId ->
            ParticipantProfileActivity.createIntent(
                this,
                participantId,
                intent.getLongExtra(TRIP_ID, 0)
            ).apply { startActivity(this) }
        }
        binding.rvCheckFriendsMember.adapter = adapter
    }

    private fun getTripId() {
        val tripId = intent.getLongExtra(EXTRA_TRIP_ID, -1L)
        viewModel.getFriendsListFromServer(tripId)
    }

    private fun observeCheckFriendsListState() {
        viewModel.checkFriendsListState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> setFriendsData(state.data)

                is UiState.Failure -> toast(getString(R.string.server_error))

                is UiState.Loading -> return@onEach

                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun setFriendsData(data: CheckFriendsModel) {
        adapter.submitList(data.participants)

        setEmptyView(data.participants)
        setProgressBarStatus(data.styles.map { it.rates })
        setCountStatus(data.styles.map { it.counts })
        setResultTextColor(data.bestPrefer)
    }

    private fun setEmptyView(participants: List<TripParticipantModel>) {
        if(participants.size == 1){

        }
    }

    private fun setProgressBarStatus(rates: List<List<Int>>) {
        with(binding) {
            val progressBars = listOf(
                progressBarCheckFriends1,
                progressBarCheckFriends2,
                progressBarCheckFriends3,
                progressBarCheckFriends4,
                progressBarCheckFriends5
            )

            for (i in rates.indices) {
                progressBars[i].progress = rates[i][0]
                progressBars[i].secondaryProgress = rates[i][0] + rates[i][1]
            }
        }
    }

    private fun setCountStatus(counts: List<List<Int>>) {
        with(binding) {
            val countsLeftList = mutableListOf(
                tvCheckFriendsPreferenceNumber1Left,
                tvCheckFriendsPreferenceNumber2Left,
                tvCheckFriendsPreferenceNumber3Left,
                tvCheckFriendsPreferenceNumber4Left,
                tvCheckFriendsPreferenceNumber5Left,
            )

            val countsCenterList = mutableListOf(
                tvCheckFriendsPreferenceNumber1Center,
                tvCheckFriendsPreferenceNumber2Center,
                tvCheckFriendsPreferenceNumber3Center,
                tvCheckFriendsPreferenceNumber4Center,
                tvCheckFriendsPreferenceNumber5Center,
            )

            val countsRightList = mutableListOf(
                tvCheckFriendsPreferenceNumber1Right,
                tvCheckFriendsPreferenceNumber2Right,
                tvCheckFriendsPreferenceNumber3Right,
                tvCheckFriendsPreferenceNumber4Right,
                tvCheckFriendsPreferenceNumber5Right
            )

            for (i in counts.indices) {
                countsLeftList[i].text =
                    getString(R.string.check_friends_preference_number, counts[i][0])
                countsCenterList[i].text =
                    getString(R.string.check_friends_preference_number, counts[i][1])
                countsRightList[i].text =
                    getString(R.string.check_friends_preference_number, counts[i][2])
            }

        }

    }

    private fun setResultTextColor(bestPrefer: List<String>) {
        binding.tvCheckFriendsResult.text = when {
            bestPrefer.isEmpty() -> {
                SpannableStringBuilder(getString(R.string.check_friends_result_no_match)).apply {
                    setSpan(
                        ForegroundColorSpan(
                            colorOf(R.color.red_500)
                        ), 0, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }

            bestPrefer.size < 5 -> {
                val bestPreferList = setBestPreferList(bestPrefer)
                val bestPreferString = bestPreferList.joinToString(separator = ", ")
                SpannableStringBuilder(
                    getString(
                        R.string.check_friends_result_match,
                        bestPreferString
                    )
                ).apply {
                    setSpan(
                        ForegroundColorSpan(colorOf(R.color.red_500)),
                        0,
                        bestPreferString.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }

            else -> {
                SpannableStringBuilder(getString(R.string.check_friends_result_perfect)).apply {
                    setSpan(
                        ForegroundColorSpan(
                            colorOf(R.color.red_500)
                        ), 0, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }
    }

    private fun setBestPreferList(bestPrefer: List<String>): List<String> {
        return bestPrefer.map { prefer ->
            when (prefer) {
                getString(R.string.check_friends_preference_1_title) -> getString(R.string.check_friends_result_match_1)
                getString(R.string.check_friends_preference_2_title) -> getString(R.string.check_friends_result_match_2)
                getString(R.string.check_friends_preference_5_title) -> getString(R.string.check_friends_result_match_5)
                else -> prefer
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }

    companion object {
        private const val TRIP_ID = "TRIP_ID"

        @JvmStatic
        fun createIntent(
            context: Context,
            tripId: Long
        ): Intent = Intent(context, CheckFriendsActivity::class.java).apply {
            putExtra(TRIP_ID, tripId)
        }
    }

}