package com.going.presentation.todo.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.ActivityPrivateDetailBinding
import com.going.presentation.todo.detail.TodoDetailActivity.Companion.EXTRA_TODO_ID
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import com.going.ui.state.EnumUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PrivateDetailActivity :
    BaseActivity<ActivityPrivateDetailBinding>(R.layout.activity_private_detail) {

    private val viewModel by viewModels<PrivateDetailViewModel>()

    private var todoId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initBackBtnClickListener()
        initDeleteBtnClickListener()
        initModBtnClickListener()
        getTodoId()
        setDetailData()
        observeTodoDetailState()
        observeTodoDeleteState()
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
            viewModel.deleteTodoFromServer(todoId)
        }
    }

    private fun initModBtnClickListener() {
        binding.btnMyTodoDetailMod.setOnSingleClickListener {
            toast(getString(R.string.will_be_update))
        }
    }

    private fun getTodoId() {
        todoId = intent.getLongExtra(EXTRA_TODO_ID, 0)
    }

    private fun setDetailData() {
        viewModel.getTodoDetailFromServer(todoId)
    }

    private fun observeTodoDetailState() {
        viewModel.todoDetailState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                EnumUiState.LOADING -> return@onEach

                EnumUiState.SUCCESS -> return@onEach

                EnumUiState.FAILURE -> toast(getString(R.string.server_error))

                EnumUiState.EMPTY -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun observeTodoDeleteState() {
        viewModel.todoDeleteState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                EnumUiState.LOADING -> return@onEach

                EnumUiState.SUCCESS -> {
                    toast(getString(R.string.todo_delete_toast))
                    finish()
                }

                EnumUiState.FAILURE -> toast(getString(R.string.server_error))

                EnumUiState.EMPTY -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

}