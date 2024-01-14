package com.going.presentation.preferencetag.entertrip

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.PreferenceData
import com.going.presentation.R
import com.going.presentation.databinding.ActivityEnterPreferenceBinding
import com.going.presentation.entertrip.starttrip.createtrip.EnterTripActivity.Companion.DAY
import com.going.presentation.entertrip.starttrip.createtrip.EnterTripActivity.Companion.END
import com.going.presentation.entertrip.starttrip.createtrip.EnterTripActivity.Companion.START
import com.going.presentation.entertrip.starttrip.createtrip.EnterTripActivity.Companion.TITLE
import com.going.presentation.entertrip.starttrip.createtrip.FinishTripActivity
import com.going.presentation.entertrip.starttrip.invitetrip.CreateTripActivity.Companion.END_DAY
import com.going.presentation.entertrip.starttrip.invitetrip.CreateTripActivity.Companion.END_MONTH
import com.going.presentation.entertrip.starttrip.invitetrip.CreateTripActivity.Companion.END_YEAR
import com.going.presentation.entertrip.starttrip.invitetrip.CreateTripActivity.Companion.NAME
import com.going.presentation.entertrip.starttrip.invitetrip.CreateTripActivity.Companion.START_DAY
import com.going.presentation.entertrip.starttrip.invitetrip.CreateTripActivity.Companion.START_MONTH
import com.going.presentation.entertrip.starttrip.invitetrip.CreateTripActivity.Companion.START_YEAR
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
        getCreateTripInfo()
        initStartBtnClickListener()
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
        binding.btnPreferenceStart.setOnSingleClickListener {
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

    private fun getCreateTripInfo() {
        val infoList = getIntent()

        if (infoList != null) {
            title = intent.getStringExtra(NAME)
            val startYear = intent.getIntExtra(START_YEAR, 0)
            val startMonth = String.format(TWO_DIGIT_FORMAT, intent.getIntExtra(START_MONTH, 0))
            val startDay = String.format(TWO_DIGIT_FORMAT, intent.getIntExtra(START_DAY, 0))
            val endYear = intent.getIntExtra(END_YEAR, 0)
            val endMonth = String.format(TWO_DIGIT_FORMAT, intent.getIntExtra(END_MONTH, 0))
            val endDay = String.format(TWO_DIGIT_FORMAT, intent.getIntExtra(END_DAY, 0))

            startDate =
                String.format(SERVER_DATE, startYear, startMonth, startDay)
            endDate = String.format(SERVER_DATE, endYear, endMonth, endDay)
        }

    }

    private fun initStartBtnClickListener() {
        binding.btnPreferenceStart.setOnSingleClickListener {
            viewModel.getTripInfoFromServer()
        }
    }

    private fun observeEnterPreferenceListState() {
        viewModel.enterPreferenceListState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    Intent(this, FinishTripActivity::class.java).apply {
                        putExtra(TITLE, state.data.title)
                        putExtra(START, state.data.startDate)
                        putExtra(END, state.data.endDate)
                        putExtra(DAY, state.data.day)
                        startActivity(this)
                    }
                }

                is UiState.Failure -> toast(getString(R.string.server_error))

                is UiState.Loading -> return@onEach

                is UiState.Empty -> return@onEach

            }
        }.launchIn(lifecycleScope)
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

    companion object {
        const val SERVER_DATE = "%s.%s.%s"
        const val TWO_DIGIT_FORMAT = "%02d"
    }
}