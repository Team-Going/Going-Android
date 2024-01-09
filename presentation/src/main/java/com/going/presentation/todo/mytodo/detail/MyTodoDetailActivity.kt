package com.going.presentation.todo.mytodo.detail

import android.os.Bundle
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityMyTodoDetailBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast

class MyTodoDetailActivity :
    BaseActivity<ActivityMyTodoDetailBinding>(R.layout.activity_my_todo_detail) {

    private val viewModel by viewModels<MyTodoDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initBackBtnClickListener()
        initDeleteBtnClickListener()
        initModBtnClickListener()
        setDetailData()
    }

    private fun initViewModel() {
        binding.vm = viewModel
    }

    private fun initBackBtnClickListener() {
        binding.btnMyTodoDetailBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun initDeleteBtnClickListener() {
        binding.btnMyTodoDetailDelete.setOnSingleClickListener {
            // 삭제 서버 통신
            finish()
        }
    }

    private fun initModBtnClickListener() {
        binding.btnMyTodoDetailMod.setOnSingleClickListener {
            toast(getString(R.string.will_be_update))
        }
    }

    private fun setDetailData() {
        // 추후 서버통신으로 변경
        viewModel.todo.value = "맛있는 밥 먹기"
        viewModel.endDate.value = "2024.1.10"
        viewModel.memo.value = "오늘 완전 완전 맛있는 파스타를 먹었는데 완전 아주 그냥 이게 말이지"
    }

}