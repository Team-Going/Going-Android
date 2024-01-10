package com.going.presentation.todo.ourtodo.detail

import android.os.Bundle
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityOurTodoDetailBinding
import com.going.presentation.todo.ourtodo.create.TodoCreateNameAdapter
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast

class OurTodoDetailActivity :
    BaseActivity<ActivityOurTodoDetailBinding>(R.layout.activity_our_todo_detail) {

    private val viewModel by viewModels<OurTodoDetailViewModel>()

    private var _adapter: TodoCreateNameAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initNameListAdapter()
        initBackBtnClickListener()
        initDeleteBtnClickListener()
        initModBtnClickListener()
        setDetailData()
    }

    private fun initViewModel() {
        binding.vm = viewModel
    }

    private fun initNameListAdapter() {
        // 아워투두 뷰에서 intent로 친구목록 받아와서 적용할 예정
        _adapter = TodoCreateNameAdapter()
        binding.rvOurTodoDetailPerson.adapter = adapter
    }

    private fun initBackBtnClickListener() {
        binding.btnOurTodoDetailBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun initDeleteBtnClickListener() {
        binding.btnOurTodoDetailDelete.setOnSingleClickListener {
            // 삭제 서버 통신
            finish()
        }
    }

    private fun initModBtnClickListener() {
        binding.btnOurTodoDetailMod.setOnSingleClickListener {
            toast(getString(R.string.will_be_update))
        }
    }

    private fun setDetailData() {
        intent.getLongExtra(EXTRA_TODO_ID,0)
        // 추후 todoId를 보내서 받는 서버통신으로 변경
        viewModel.todo.value = "맛있는 밥 먹기"
        viewModel.endDate.value = "2024.1.10"
        adapter.submitList(listOf("김상호", "박동민", "조세연", "이유빈"))
        viewModel.memo.value = "오늘 완전 완전 맛있는 파스타를 먹었는데 완전 아주 그냥 이게 말이지"
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
       }

    companion object {
        const val EXTRA_TODO_ID = "EXTRA_TODO_ID"
    }
}