package com.going.presentation.entertrip.starttrip.invitetrip

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.going.domain.entity.NameState
import com.going.presentation.R
import com.going.presentation.databinding.ActivityCreateTripBinding
import com.going.presentation.entertrip.starttrip.StartTripSplashActivity
import com.going.presentation.entertrip.starttrip.createtrip.BottomSheetDateContentFragment
import com.going.presentation.entertrip.starttrip.createtrip.EnterPreferenceActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class CreateTripActivity :
    BaseActivity<ActivityCreateTripBinding>(R.layout.activity_create_trip) {
    private val viewModel by viewModels<CreateTripViewModel>()

    private lateinit var startBottomSheetDialog: BottomSheetDateContentFragment
    private lateinit var endBottomSheetDialog: BottomSheetDateContentFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBackBtnClickListener()
        initBindingViewModel()
        observeTextLength()
        observeIsNameAvailable()
        observeCheckStartDateAvailable()
        observeCheckEndDateAvailable()
        initStartDateClickListener()
        initEndDateClickListener()
        initNextBtnClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.tbCreateTrip.setOnSingleClickListener {
            Intent(this, StartTripSplashActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun initBindingViewModel() {
        binding.viewModel = viewModel
    }

    private fun observeTextLength() {
        viewModel.nameLength.observe(this) { length ->
            val maxNameLength = viewModel.getMaxNameLen()

            if (length > maxNameLength) {
                binding.etCreateTripName.apply {
                    setText(text?.subSequence(0, maxNameLength))
                    setSelection(maxNameLength)
                }
            }
        }
    }

    private fun observeIsNameAvailable() {
        viewModel.isNameAvailable.observe(this) { state ->
            setColors(
                false,
                viewModel.nameLength.value ?: 0,
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
                    binding.tvCreateTripStartDate
                )
                { background ->
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
                    binding.tvCreateTripEndDate
                )
                { background ->
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
        hasFocus: Boolean,
        length: Int,
        counter: TextView,
        setBackground: (Int) -> Unit,
    ) {
        val (color, background) = when {
            viewModel.isNameAvailable.value != NameState.Blank && hasFocus -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
            length == 0 -> R.color.gray_200 to R.drawable.shape_rect_4_gray200_line
            viewModel.isNameAvailable.value == NameState.Blank && counter == binding.tvNameCounter -> R.color.red_500 to R.drawable.shape_rect_4_red500_line
            else -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
        }
        setCounterColor(counter, color)
        setBackground(background)
    }

    private fun setStartDateColors(
        date: TextView,
        setDatecolor: (Int) -> Unit,
    ) {
        val (color, background) = when {
            viewModel.isStartDateAvailable.value == true -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
            else -> R.color.gray_200 to R.drawable.shape_rect_4_gray200_line
        }
        setDateColor(date, color)
        setDatecolor(background)
    }

    private fun setEndDateColors(
        date: TextView,
        setDatecolor: (Int) -> Unit,
    ) {
        val (color, background) = when {
            viewModel.isEndDateAvailable.value == true -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
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
            startBottomSheetDialog = BottomSheetDateContentFragment(viewModel, true)
            startBottomSheetDialog.show(supportFragmentManager, startBottomSheetDialog.tag)
        }
    }

    private fun initEndDateClickListener() {
        binding.tvCreateTripEndDate.setOnSingleClickListener {
            endBottomSheetDialog = BottomSheetDateContentFragment(viewModel, false)
            endBottomSheetDialog.show(supportFragmentManager, endBottomSheetDialog.tag)
        }
    }

    private fun initNextBtnClickListener() {
        binding.btnCreateTripNext.setOnSingleClickListener {
            Intent(this, EnterPreferenceActivity::class.java).apply {
                putExtra(NAME, viewModel.name.value)
                putExtra(START_YEAR, viewModel.startYear.value)
                putExtra(START_MONTH, viewModel.startMonth.value)
                putExtra(START_DAY, viewModel.startDay.value)
                putExtra(END_YEAR, viewModel.endYear.value)
                putExtra(END_MONTH, viewModel.endMonth.value)
                putExtra(END_DAY, viewModel.endDay.value)
                startActivity(this)
            }
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



