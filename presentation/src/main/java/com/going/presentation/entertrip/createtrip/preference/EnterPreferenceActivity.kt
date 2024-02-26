package com.going.presentation.entertrip.createtrip.preference

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.PreferenceData
import com.going.presentation.R
import com.going.presentation.databinding.ActivityEnterPreferenceBinding
import com.going.presentation.entertrip.createtrip.choosedate.CreateTripActivity.Companion.INTENT_DATA
import com.going.presentation.entertrip.createtrip.finish.FinishTripActivity
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.DAY
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.END
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.INVITE_CODE
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.START
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.TITLE
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.TRIP_ID
import com.going.presentation.entertrip.preferencetag.PreferenceTagAdapter
import com.going.presentation.entertrip.preferencetag.PreferenceTagDecoration
import com.going.ui.base.BaseActivity
import com.going.ui.extension.getParcelable
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import com.going.ui.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class EnterPreferenceActivity :
    BaseActivity<ActivityEnterPreferenceBinding>(R.layout.activity_enter_preference),
    PreferenceTagAdapter.OnPreferenceSelectedListener {

    private var _adapter: PreferenceTagAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by viewModels<EnterPreferenceViewModel>()

    private val preferenceAnswers = MutableList(5) { Int.MAX_VALUE }

    private var title: String? = ""
    private var startDate: String? = ""
    private var endDate: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()
        initItemDecoration()
        initBackClickListener()
        initStartBtnClickListener()
        getCreateTripInfo()
        observeEnterPreferenceListState()

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

    private fun initBackClickListener() {
        binding.btnPreferenceBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun initStartBtnClickListener() {
        binding.btnPreferenceStart.setOnSingleClickListener {
            viewModel.getTripInfoFromServer()
        }
    }

    private fun getCreateTripInfo() {
        val data = intent?.getParcelable(INTENT_DATA, IntentData::class.java)

        title = data?.name
        val startYear = data?.startYear ?: 0
        val startMonth = String.format(TWO_DIGIT_FORMAT, data?.startMonth ?: 0)
        val startDay = String.format(TWO_DIGIT_FORMAT, data?.startDay ?: 0)
        val endYear = data?.endYear ?: 0
        val endMonth = String.format(TWO_DIGIT_FORMAT, data?.endMonth ?: 0)
        val endDay = String.format(TWO_DIGIT_FORMAT, data?.endDay ?: 0)
        startDate = String.format(SERVER_DATE, startYear, startMonth, startDay)
        endDate = String.format(SERVER_DATE, endYear, endMonth, endDay)
    }

    private fun observeEnterPreferenceListState() {
        viewModel.enterPreferenceListState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    Intent(this, FinishTripActivity::class.java).apply {
                        putExtra(TITLE, state.data.title)
                        putExtra(START, state.data.startDate)
                        putExtra(END, state.data.endDate)
                        putExtra(INVITE_CODE, state.data.code)
                        putExtra(DAY, state.data.day)
                        putExtra(TRIP_ID, state.data.tripId)
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(this)
                    }
                    finish()
                }

                is UiState.Failure -> toast(getString(R.string.server_error))

                is UiState.Loading -> return@onEach

                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun isButtonValid() {
        val isValid = preferenceAnswers.all { it != Int.MAX_VALUE }

        if (isValid) {
            binding.btnPreferenceStart.isEnabled = isValid
            binding.btnPreferenceStart.setTextColor(
                ContextCompat.getColorStateList(this, R.color.white_000),
            )
        }
    }

    private fun sendTripInfo() {
        viewModel.title.value = title
        viewModel.startDate.value = startDate
        viewModel.endDate.value = endDate
        viewModel.styleA.value = preferenceAnswers[0]
        viewModel.styleB.value = preferenceAnswers[1]
        viewModel.styleC.value = preferenceAnswers[2]
        viewModel.styleD.value = preferenceAnswers[3]
        viewModel.styleE.value = preferenceAnswers[4]
    }

    override fun onPreferenceSelected(item: PreferenceData, checkList: Int) {
        preferenceAnswers[item.number.toInt() - 1] = checkList
        isButtonValid()
        sendTripInfo()
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }

    @Parcelize
    data class IntentData(
        val name: String,
        val startYear: Int,
        val startMonth: Int,
        val startDay: Int,
        val endYear: Int,
        val endMonth: Int,
        val endDay: Int
    ) : Parcelable


    companion object {
        const val SERVER_DATE = "%s.%s.%s"
        const val TWO_DIGIT_FORMAT = "%02d"

        @JvmStatic
        fun createIntent(
            context: Context, data: IntentData
        ): Intent = Intent(context, EnterPreferenceActivity::class.java).apply {
            putExtra(INTENT_DATA, data)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
    }
}
