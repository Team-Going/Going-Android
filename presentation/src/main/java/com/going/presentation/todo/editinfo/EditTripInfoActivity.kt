package com.going.presentation.todo.editinfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.ActivityEditTripInfoBinding
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditTripInfoActivity :
    BaseActivity<ActivityEditTripInfoBinding>(R.layout.activity_edit_trip_info) {
    private val viewModel by viewModels<EditTripInfoViewModel>()

    private var startBottomSheetDialog: EditDateBottomSheet? = null
    private var endBottomSheetDialog: EditDateBottomSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBindingViewModel()
        getServerList()
        setEtInfoNameArguments()
        observeInfoNameTextChanged()
        initStartDateClickListener()
        initEndDateClickListener()
        initEditBtnClickListener()
        initBackBtnClickListener()
    }

    private fun initBindingViewModel() {
        binding.viewModel = viewModel
    }

    private fun getServerList() {
        val title = intent.getStringExtra(INFO_TITLE)
        val startDate = intent.getStringExtra(INFO_START_DATE)
        val endDate = intent.getStringExtra(INFO_END_DATE)

        with(binding) {
            etEditTripInfoName.editText?.setText(title)
            tvEditTripInfoStartDate.text = startDate
            tvEditTripInfoEndDate.text = endDate
        }
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
            viewModel.setNameState(text.toString(), binding.etEditTripInfoName.state)
        }
    }


    private fun initStartDateClickListener() {
        binding.tvEditTripInfoStartDate.setOnSingleClickListener {
            startBottomSheetDialog = EditDateBottomSheet(true)
            startBottomSheetDialog?.show(supportFragmentManager, startBottomSheetDialog?.tag)
        }
    }

    private fun initEndDateClickListener() {
        binding.tvEditTripInfoEndDate.setOnSingleClickListener {
            if (viewModel.startYear.value != null && viewModel.startMonth.value != null && viewModel.startDay.value != null) {
                endBottomSheetDialog = EditDateBottomSheet(false)
                endBottomSheetDialog?.show(supportFragmentManager, endBottomSheetDialog?.tag)
            } else {
                toast(getString(R.string.create_trip_toast_error))
            }
        }
    }

    private fun initEditBtnClickListener() {
        binding.btnEditTripSave.setOnSingleClickListener {
            Intent(this, DashBoardActivity::class.java).apply {
                putExtra(NAME, viewModel.name.value)
                putExtra(START_YEAR, viewModel.startYear.value)
                putExtra(START_MONTH, viewModel.startMonth.value)
                putExtra(START_DAY, viewModel.startDay.value)
                putExtra(END_YEAR, viewModel.endYear.value)
                putExtra(END_MONTH, viewModel.endMonth.value)
                putExtra(END_DAY, viewModel.endDay.value)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(this)
            }
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnEditTripInfoBack.setOnSingleClickListener {
            //다른 뷰로 이동
            finish()
        }
    }

    companion object {
        const val NAME = "name"
        const val INFO_TITLE = "title"
        const val START_YEAR = "startYear"
        const val START_MONTH = "startMonth"
        const val START_DAY = "startDay"
        const val INFO_START_DATE = "INFO_START_DATE"
        const val END_YEAR = "endYear"
        const val END_MONTH = "endMonth"
        const val END_DAY = "endDay"
        const val INFO_END_DATE = "INFO_END_DATE"

        @JvmStatic
        fun createIntent(
            context: Context,
            title: String,
            startDate: String,
            endDate: String,
        ): Intent = Intent(context, EditTripActivity::class.java).apply {
            putExtra(INFO_TITLE, title)
            putExtra(INFO_START_DATE, startDate)
            putExtra(INFO_END_DATE, endDate)
        }
    }
}

