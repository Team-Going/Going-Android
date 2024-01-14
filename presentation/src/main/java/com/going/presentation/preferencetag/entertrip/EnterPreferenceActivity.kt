package com.going.presentation.preferencetag.entertrip

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.going.domain.entity.PreferenceData
import com.going.presentation.R
import com.going.presentation.databinding.ActivityEnterPreferenceBinding
import com.going.presentation.preferencetag.PreferenceTagAdapter
import com.going.presentation.preferencetag.PreferenceTagDecoration
import com.going.presentation.starttrip.createtrip.CreateTripActivity.Companion.END_DAY
import com.going.presentation.starttrip.createtrip.CreateTripActivity.Companion.END_MONTH
import com.going.presentation.starttrip.createtrip.CreateTripActivity.Companion.END_YEAR
import com.going.presentation.starttrip.createtrip.CreateTripActivity.Companion.NAME
import com.going.presentation.starttrip.createtrip.CreateTripActivity.Companion.START_DAY
import com.going.presentation.starttrip.createtrip.CreateTripActivity.Companion.START_MONTH
import com.going.presentation.starttrip.createtrip.CreateTripActivity.Companion.START_YEAR
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

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
        sendStyleInfo()

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
            sendStyleInfo()
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
            val startMonth = intent.getIntExtra(START_MONTH, 0)
            val startDay = intent.getIntExtra(START_DAY, 0)
            val endYear = intent.getIntExtra(END_YEAR, 0)
            val endMonth = intent.getIntExtra(END_MONTH, 0)
            val endDay = intent.getIntExtra(END_DAY, 0)

            startDate = String.format(SERVER_DATE, startYear, startMonth, startDay)
            endDate = String.format(SERVER_DATE, endYear, endMonth, endDay)
        }

    }

    private fun sendStyleInfo() {
        var styleA = preferenceAnswers[0]
    }

    override fun onPreferenceSelected(item: PreferenceData, checkList: Int) {
        preferenceAnswers[item.number.toInt() - 1] = checkList
        isButtonValid()
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }

    companion object {
        const val SERVER_DATE = "%s.%s.%s"
    }
}