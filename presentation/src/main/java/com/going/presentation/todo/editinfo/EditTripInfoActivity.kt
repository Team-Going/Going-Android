package com.going.presentation.todo.editinfo

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.ActivityEditTripInfoBinding
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

    //섭통 해서 화면에 넣어서 보여줘야함

    private fun initBackBtnClickListener() {
        binding.btnEditTripInfoBack.setOnSingleClickListener {
            //다른 뷰로 이동
            finish()
        }
    }

    companion object {
        const val NAME = "name"
        const val START_YEAR = "startYear"
        const val START_MONTH = "startMonth"
        const val START_DAY = "startDay"
        const val END_YEAR = "endYear"
        const val END_MONTH = "endMonth"
        const val END_DAY = "endDay"
    }
}

