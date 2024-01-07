package com.going.presentation.createtrip

import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import com.going.domain.entity.NameState
import com.going.presentation.DateBottomSheet
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
        initStartDateClickListener()
        initEndDateClickListener()


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

    private fun setCounterColor(counter: TextView, color: Int) {
        counter.setTextColor(getColor(color))
    }

    private fun initBtnClickListener(){
        binding.btnCreateTripFinish.setOnSingleClickListener{
            // 년 월 일 값 받아오고 다시 create_trip 액티비티로 돌아감
        }
    }

    private fun initStartDateClickListener() {
        binding.tvCreateTripStartDate.setOnSingleClickListener {
            val bottomSheetDialog = DateBottomSheet()
            bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)

            initBtnClickListener()
            //val year: DatePicker =
        }
    }

    private fun initEndDateClickListener() {
        binding.tvCreateTripEndDate.setOnSingleClickListener {
            val bottomSheetDialog = DateBottomSheet()
            bottomSheetDialog.show(supportFragmentManager, bottomSheetDialog.tag)

            initBtnClickListener()
        }
    }


}

//
//    private fun moveSplash() {
//        // 스플래시로 이동
//    }
