package com.going.presentation.todo.editinfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.ActivityEditTripBinding
import com.going.presentation.todo.TodoActivity
import com.going.presentation.todo.create.TodoCreateActivity
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
                //섭통 결과물
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(this)
            }
        }
    }

    private fun getTripInfo() {
        //인텐트 보내면 받기
    }

    private fun setTripInfo() {
        //binding.tv~  뷰에 직접적으로 넣어버리자
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

    companion object{
        private const val TRIP_ID = "TRIP_ID"
        private const val TITLE = "TITLE"
        private const val START_DATE = "START_DATE"
        private const val END_DATE = "END_DATE"

        @JvmStatic
        fun createIntent(
            context: Context,
            tripId: Long,
            title: String,
            startDate: String,
            endDate: String
        ): Intent = Intent(context, EditTripActivity::class.java).apply {
            putExtra(TRIP_ID, tripId)
            putExtra(TITLE, title)
            putExtra(START_DATE, startDate)
            putExtra(END_DATE, endDate)
        }
    }
}