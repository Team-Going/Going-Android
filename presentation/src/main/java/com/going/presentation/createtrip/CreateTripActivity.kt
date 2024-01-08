package com.going.presentation.createtrip

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.going.domain.entity.NameState
import com.going.presentation.R
import com.going.presentation.databinding.ActivityCreateTripBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class CreateTripActivity :
    BaseActivity<ActivityCreateTripBinding>(R.layout.activity_create_trip) {
    private val viewModel by viewModels<CreateTripViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBindingViewModel()
        observeIsNameAvailable()
        observeCheckStartDateAvailable()
        observeCheckEndDateAvailable()
        initStartDateClickListener()
        initEndDateClickListener()
        initNextBtnClickListener()
    }

    private fun initBindingViewModel() {
        binding.viewModel = viewModel
    }


    private fun observeIsNameAvailable() {
        viewModel.isNameAvailable.observe(this) { state ->
            setColors(
                false,
                viewModel.NameLength.value ?: 0,
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
                binding.tvCreateTripStartDate)
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
                    binding.tvCreateTripEndDate)
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
        setDateColor(date,color)
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
        setDateColor(date,color)
        setDatecolor(background)
    }

    private fun setDateColor(date: TextView, color: Int){
        date.setTextColor(getColor(color))
    }

    private fun setCounterColor(counter: TextView, color: Int) {
        counter.setTextColor(getColor(color))
    }


    private fun initStartDateClickListener() {
        binding.tvCreateTripStartDate.setOnSingleClickListener {
            val bottomSheetDialog = DateBottomSheet(viewModel, true)
            bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
        }
    }

    private fun initEndDateClickListener() {
        binding.tvCreateTripEndDate.setOnSingleClickListener {
            val bottomSheetDialog = DateBottomSheet(viewModel, false)
            bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)
        }
    }

    private fun initNextBtnClickListener() {
        binding.btnCreateTripNext.setOnSingleClickListener {
            //성향 태그로 움직임
            finish()
        }
    }
}

    private fun moveSplash() {
        // 스플래시로 이동
    }



