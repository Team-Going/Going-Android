package com.going.presentation.todo.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.ActivityPublicDetailBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.EnumUiState
import com.going.ui.extension.UiState
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PublicDetailActivity :
    BaseActivity<ActivityPublicDetailBinding>(R.layout.activity_public_detail) {

    private val viewModel by viewModels<PublicDetailViewModel>()

    private var _adapter: TodoDetailNameAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private var todoId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initNameListAdapter()
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

    private fun initNameListAdapter() {
        _adapter = TodoDetailNameAdapter()
        binding.rvOurTodoDetailPerson.adapter = adapter
    }

    private fun initBackBtnClickListener() {
        binding.btnOurTodoDetailBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun initDeleteBtnClickListener() {
        binding.btnOurTodoDetailDelete.setOnSingleClickListener {
            viewModel.deleteTodoFromServer(todoId)
        }
    }

    private fun initModBtnClickListener() {
        binding.btnOurTodoDetailMod.setOnSingleClickListener {
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
                is UiState.Loading -> return@onEach

                is UiState.Success -> adapter.submitList(state.data)

                is UiState.Failure -> toast(getString(R.string.server_error))

                is UiState.Empty -> return@onEach
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

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }

    companion object {
        const val EXTRA_TODO_ID = "EXTRA_TODO_ID"
    }
}