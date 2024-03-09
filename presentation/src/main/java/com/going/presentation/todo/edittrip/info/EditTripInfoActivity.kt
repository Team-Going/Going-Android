package com.going.presentation.todo.edittrip.info

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.ActivityEditTripInfoBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class EditTripInfoActivity :
    BaseActivity<ActivityEditTripInfoBinding>(R.layout.activity_edit_trip_info) {
    private val viewModel by viewModels<EditTripInfoViewModel>()

    private var startBottomSheetDialog: EditDateBottomSheet? = null
    private var endBottomSheetDialog: EditDateBottomSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBindingViewModel()
        observePatchEditState()
        setEtInfoNameArguments()
        observeInfoNameTextChanged()
        getTripInfoData()
        initStartDateClickListener()
        initEndDateClickListener()
        initEditBtnClickListener()
        initBackBtnClickListener()
    }

    private fun initBindingViewModel() {
        binding.viewModel = viewModel
    }

    private fun getTripInfoData() {
        viewModel.tripId = intent.getLongExtra(TRIP_ID, -1L)
        viewModel.currentTitle = intent.getStringExtra(TITLE) ?:""
        viewModel.currentStartDate = intent.getStringExtra(START_DATE) ?: ""
        viewModel.currentEndDate = intent.getStringExtra(END_DATE) ?: ""

        binding.etEditTripInfoName.editText.setText(viewModel.currentTitle)

        val (startYear, startMonth, startDay) = splitDate(viewModel.currentStartDate)
        viewModel.currentStartYear.value = startYear
        viewModel.currentStartMonth.value = startMonth
        viewModel.currentStartDay.value = startDay
        viewModel.setStartDate(startYear,startMonth,startDay)

        val (endYear, endMonth, endDay) = splitDate(viewModel.currentEndDate)
        viewModel.currentEndYear.value = endYear
        viewModel.currentEndMonth.value = endMonth
        viewModel.currentEndDay.value = endDay
        viewModel.setEndDate(endYear,endMonth,endDay)
    }

    fun splitDate(date: String): Triple<Int, Int, Int> {
        val parts = date.split(".")
        val year = parts[0].toInt()
        val month = parts[1].toInt()
        val day = parts[2].toInt()
        return Triple(year, month, day)
    }

    private fun observePatchEditState() {
        viewModel.tripEditState.flowWithLifecycle(lifecycle).onEach { result ->
            if (result) {
                toast(getString(R.string.edit_trip_toast_success))
                setResult(Activity.RESULT_OK)
                finish()
                return@onEach
            }
            toast(getString(R.string.edit_trip_toast_failure))
        }.launchIn(lifecycleScope)
    }

    private fun setEtInfoNameArguments() {
        with(binding.etEditTripInfoName) {
            setMaxLen(viewModel.getMaxTripLen())
            overWarning = getString(R.string.trip_over_error)
            blankWarning = getString(R.string.trip_blank_error)
        }
    }


    private fun observeInfoNameTextChanged() {
        binding.etEditTripInfoName.editText.doAfterTextChanged { text ->
            viewModel.setTitleState(text.toString(), binding.etEditTripInfoName.state)
        }
        viewModel.checkTripAvailable()
    }


    private fun initStartDateClickListener() {
        binding.tvEditTripInfoStartDate.setOnSingleClickListener {
            startBottomSheetDialog = EditDateBottomSheet(true)
            startBottomSheetDialog?.show(supportFragmentManager, startBottomSheetDialog?.tag)
        }
        viewModel.checkStartDateAvailable()
        viewModel.checkTripAvailable()

    }

    private fun initEndDateClickListener() {
        binding.tvEditTripInfoEndDate.setOnSingleClickListener {
                endBottomSheetDialog = EditDateBottomSheet(false)
                endBottomSheetDialog?.show(supportFragmentManager, endBottomSheetDialog?.tag)
        }
        viewModel.checkEndDateAvailable()
        viewModel.checkTripAvailable()

    }

    private fun initEditBtnClickListener() {
        binding.btnEditTripSave.setOnSingleClickListener {
            viewModel.patchTodoToServer()
            val intent = Intent(this, DashBoardActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnEditTripInfoBack.setOnSingleClickListener {
            finish()
        }
    }

    companion object {
        const val TRIP_ID = "TRIP_ID"
        const val TITLE = "TITLE"
        const val START_DATE = "START_DATE"
        const val END_DATE = "END_DATE"

        @JvmStatic
        fun createIntent(
            context: Context,
            tripId: Long,
            title: String,
            startDate: String,
            endDate: String
        ): Intent = Intent(context, EditTripInfoActivity::class.java).apply {
            putExtra(TRIP_ID, tripId)
            putExtra(TITLE, title)
            putExtra(START_DATE, startDate)
            putExtra(END_DATE, endDate)
        }
    }
}

