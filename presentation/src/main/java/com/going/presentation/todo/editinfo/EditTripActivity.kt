package com.going.presentation.todo.editinfo

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.ActivityEditTripBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class EditTripActivity :
    BaseActivity<ActivityEditTripBinding>(R.layout.activity_edit_trip) {
    private val viewModel by viewModels<EditTripViewModel>()
    private var quitDialog: TripQuitDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBindingViewModel()
        initEditBtnClickListener()
        initQuitBtnClickListener()
        showQuitDialog()
        initBackBtnClickListener()
    }

    private fun initBindingViewModel() {
        binding.viewModel = viewModel
    }

    private fun initQuitBtnClickListener() {
        binding.btnEditTripQuit.setOnSingleClickListener {
            showQuitDialog()
        }
    }

    private fun initEditBtnClickListener() {
        binding.btnEditTripEdit.setOnSingleClickListener {
            Intent(this, EditTripInfoActivity::class.java).apply {
                putExtra(EditTripInfoActivity.NAME, viewModel.name.value)
                putExtra(EditTripInfoActivity.START_YEAR, viewModel.startYear.value)
                putExtra(EditTripInfoActivity.START_MONTH, viewModel.startMonth.value)
                putExtra(EditTripInfoActivity.START_DAY, viewModel.startDay.value)
                putExtra(EditTripInfoActivity.END_YEAR, viewModel.endYear.value)
                putExtra(EditTripInfoActivity.END_MONTH, viewModel.endMonth.value)
                putExtra(EditTripInfoActivity.END_DAY, viewModel.endDay.value)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(this)
            }
        }
    }

    //인텐트 보내면 받기

    private fun getTripInfo() {
        //인텐트 보내면 받기
    }

    private fun setTripInfo() {
        //binding.tv~  뷰에 직접적으로 생기게
    }

    private fun showQuitDialog() {
        quitDialog = TripQuitDialogFragment()
        quitDialog?.show(supportFragmentManager, quitDialog?.tag)
        Intent(this, DashBoardActivity::class.java).apply {
            //정보 지워지게 구성
            startActivity(this)
        }
    }

    private fun initBackBtnClickListener() {
        binding.btnEditTripInfoBack.setOnSingleClickListener {
            //어느 뷰로 가는지
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (quitDialog?.isAdded == true) quitDialog?.dismiss()
    }
}