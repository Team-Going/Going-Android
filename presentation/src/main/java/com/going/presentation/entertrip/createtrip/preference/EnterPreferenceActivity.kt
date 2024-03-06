package com.going.presentation.entertrip.createtrip.preference

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.response.EnterPreferenceModel
import com.going.presentation.R
import com.going.presentation.databinding.ActivityEnterPreferenceBinding
import com.going.presentation.entertrip.createtrip.choosedate.CreateTripActivity.Companion.TRIP_INTENT_DATA
import com.going.presentation.entertrip.createtrip.finish.FinishTripActivity
import com.going.presentation.entertrip.preferencetag.PreferenceTagAdapter
import com.going.presentation.entertrip.preferencetag.PreferenceTagDecoration
import com.going.ui.base.BaseActivity
import com.going.ui.extension.getParcelable
import com.going.ui.extension.colorOf
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import com.going.ui.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class EnterPreferenceActivity :
    BaseActivity<ActivityEnterPreferenceBinding>(R.layout.activity_enter_preference) {

    private var _adapter: PreferenceTagAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by viewModels<EnterPreferenceViewModel>()

    private val preferenceAnswers = MutableList(5) { Int.MAX_VALUE }

    private var title: String? = ""
    private var startDate: String? = ""
    private var endDate: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapterWithClickListener()
        initItemDecoration()
        initBackClickListener()
        initStartBtnClickListener()
        getCreateTripInfo()
        observeEnterPreferenceListState()

    }

    private fun initAdapterWithClickListener() {
        _adapter = PreferenceTagAdapter(
            this
        ) { item, checkedIndex ->
            preferenceAnswers[item.number.toInt() - 1] = checkedIndex
            isButtonValid()
            sendTripInfo()
        }
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
        val data = intent?.getParcelable(TRIP_INTENT_DATA, ParcelableTripData::class.java)

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
                is UiState.Success -> navigateToFinishTrip(state.data)

                is UiState.Failure -> toast(getString(R.string.server_error))

                is UiState.Loading -> return@onEach

                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToFinishTrip(data: EnterPreferenceModel) {
        FinishTripActivity.createIntent(
            this,
            data
        ).apply { startActivity(this) }
        finish()
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

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }

    companion object {
        const val SERVER_DATE = "%s.%s.%s"
        const val TWO_DIGIT_FORMAT = "%02d"

        @JvmStatic
        fun createIntent(
            context: Context, data: ParcelableTripData
        ): Intent = Intent(context, EnterPreferenceActivity::class.java).apply {
            putExtra(TRIP_INTENT_DATA, data)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
    }
}
