package com.going.presentation.todo.editinfo

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.going.domain.entity.NameState
import com.going.presentation.R
import com.going.presentation.databinding.ActivityEditTripInfoBinding
import com.going.presentation.entertrip.createtrip.preference.EnterPreferenceActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast

class EditTripInfoActivity :
    BaseActivity<ActivityEditTripInfoBinding>(R.layout.activity_edit_trip_info) {
    private val viewModel by viewModels<EditTripInfoViewModel>()

    private lateinit var startBottomSheetDialog: BottomSheetEditDateFragment
    private lateinit var endBottomSheetDialog: BottomSheetEditDateFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBindingViewModel()
        observeIsNameAvailable()
        initStartDateClickListener()
        initEndDateClickListener()
        initNextBtnClickListener()
        initQuitBtnClickListener()
        initBackBtnClickListener()
    }

    private fun initBindingViewModel() {
        binding.viewModel = viewModel
    }

    private fun observeIsNameAvailable() {
        viewModel.isNameAvailable.observe(this) { state ->
            setColors(
                state,
                binding.tvNameCounter,
            ) { background ->
                binding.etEditTripInfoName.background = ResourcesCompat.getDrawable(
                    this.resources,
                    background,
                    theme,
                )
            }
        }
    }

    private fun setColors(
        state: NameState,
        counter: TextView,
        setBackground: (Int) -> Unit,
    ) {
        val (color, background) = when (state) {
            NameState.Empty -> R.color.gray_200 to R.drawable.shape_rect_4_gray200_line
            NameState.Success -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
            NameState.Blank -> R.color.red_500 to R.drawable.shape_rect_4_red500_line
            NameState.OVER -> R.color.red_500 to R.drawable.shape_rect_4_red500_line
        }

        setCounterColor(counter, color)
        setBackground(background)
    }

    private fun setCounterColor(counter: TextView, color: Int) {
        counter.setTextColor(getColor(color))
    }

    private fun initStartDateClickListener() {
        binding.tvEditTripInfoStartDate.setOnSingleClickListener {
            startBottomSheetDialog = BottomSheetEditDateFragment(viewModel, true)
            startBottomSheetDialog.show(supportFragmentManager, startBottomSheetDialog.tag)
        }
    }

    private fun initEndDateClickListener() {
        binding.tvEditTripInfoEndDate.setOnSingleClickListener {

            if (viewModel.startYear.value != null && viewModel.startMonth.value != null && viewModel.startDay.value != null) {
                endBottomSheetDialog = BottomSheetEditDateFragment(viewModel, false)
                endBottomSheetDialog.show(supportFragmentManager, endBottomSheetDialog.tag)
            } else {
                toast(getString(R.string.create_trip_toast_error))
            }
        }
    }

    private fun initNextBtnClickListener() {
        //어느 뷰로 보내는지 확실히
        binding.btnEditTripInfoEdit.setOnSingleClickListener {
            Intent(this, EnterPreferenceActivity::class.java).apply {
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

    private fun initQuitBtnClickListener() {
        binding.btnEditTripInfoBack.setOnSingleClickListener {
            //다이얼로그 띄우기
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnEditTripInfoBack.setOnSingleClickListener {
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

