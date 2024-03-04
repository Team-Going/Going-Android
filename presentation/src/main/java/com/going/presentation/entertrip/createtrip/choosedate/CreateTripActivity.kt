package com.going.presentation.entertrip.createtrip.choosedate

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.going.domain.entity.NameState
import com.going.presentation.R
import com.going.presentation.databinding.ActivityCreateTripBinding
import com.going.presentation.entertrip.createtrip.preference.EnterPreferenceActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast

class CreateTripActivity : BaseActivity<ActivityCreateTripBinding>(R.layout.activity_create_trip) {
    private val viewModel by viewModels<CreateTripViewModel>()

    private var startBottomSheetDialog: DateContentBottomSheet? = null
    private var endBottomSheetDialog: DateContentBottomSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBindingViewModel()
        observeIsNameAvailable()
        observeCheckStartDateAvailable()
        observeCheckEndDateAvailable()
        initStartDateClickListener()
        initEndDateClickListener()
        initNextBtnClickListener()
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
                binding.etCreateTripName.background = ResourcesCompat.getDrawable(
                    this.resources,
                    background,
                    theme,
                )
            }
        }
    }

    private fun observeCheckStartDateAvailable() {
        viewModel.isStartDateAvailable.observe(this) { isAvailable ->
            if (isAvailable) {
                setStartDateColors(
                    binding.tvCreateTripStartDate,
                ) { background ->
                    binding.tvCreateTripStartDate.background = ResourcesCompat.getDrawable(
                        this.resources,
                        background,
                        theme,
                    )
                }
            }
        }
    }

    private fun observeCheckEndDateAvailable() {
        viewModel.isEndDateAvailable.observe(this) { isAvailable ->
            if (isAvailable) {
                setEndDateColors(
                    binding.tvCreateTripEndDate,
                ) { background ->
                    binding.tvCreateTripEndDate.background = ResourcesCompat.getDrawable(
                        this.resources,
                        background,
                        theme,
                    )
                }
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

    private fun setStartDateColors(
        date: TextView,
        setDatecolor: (Int) -> Unit,
    ) {
        val (color, background) = when (viewModel.isStartDateAvailable.value) {
            true -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
            else -> R.color.gray_200 to R.drawable.shape_rect_4_gray200_line
        }
        setDateColor(date, color)
        setDatecolor(background)
    }

    private fun setEndDateColors(
        date: TextView,
        setDatecolor: (Int) -> Unit,
    ) {
        val (color, background) = when (viewModel.isEndDateAvailable.value) {
            true -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
            else -> R.color.gray_200 to R.drawable.shape_rect_4_gray200_line
        }
        setDateColor(date, color)
        setDatecolor(background)
    }

    private fun setDateColor(date: TextView, color: Int) {
        date.setTextColor(getColor(color))
    }

    private fun setCounterColor(counter: TextView, color: Int) {
        counter.setTextColor(getColor(color))
    }

    private fun initStartDateClickListener() {
        binding.tvCreateTripStartDate.setOnSingleClickListener {
            startBottomSheetDialog = DateContentBottomSheet(true)
            startBottomSheetDialog?.show(supportFragmentManager, startBottomSheetDialog?.tag)
        }
    }

    private fun initEndDateClickListener() {
        binding.tvCreateTripEndDate.setOnSingleClickListener {
            if (viewModel.startYear.value != null && viewModel.startMonth.value != null && viewModel.startDay.value != null) {
                endBottomSheetDialog = DateContentBottomSheet(false)
                endBottomSheetDialog?.show(supportFragmentManager, endBottomSheetDialog?.tag)
            } else {
                toast(getString(R.string.create_trip_toast_error))
            }
        }
    }

    private fun initNextBtnClickListener() {
        binding.btnCreateTripNext.setOnSingleClickListener {
            viewModel.saveIntentData()
            viewModel.tripIntentData?.let {
                EnterPreferenceActivity.createIntent(
                    this,
                    it
                ).apply { startActivity(this) }
            }
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnCreateBack.setOnSingleClickListener {
            finish()
        }
    }

    companion object {
        const val TRIP_INTENT_DATA = "tripIntentData"
    }
}
