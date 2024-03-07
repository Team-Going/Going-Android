package com.going.presentation.todo.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTodoDetailBinding
import com.going.presentation.todo.change.TodoChangeActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.colorOf
import com.going.ui.extension.drawableOf
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.stringOf
import com.going.ui.extension.toast
import com.going.ui.state.EnumUiState
import com.going.ui.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TodoDetailActivity :
    BaseActivity<ActivityTodoDetailBinding>(R.layout.activity_todo_detail) {

    private val viewModel by viewModels<TodoDetailViewModel>()

    private var _adapter: TripAllocatorAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initBackBtnClickListener()
        initDeleteBtnClickListener()
        initModBtnClickListener()
        getIntentData()
        initAllocatorListAdapter()
        resetDetailData()
        observeTodoDetailState()
        observeTodoDeleteState()
    }

    private fun initViewModel() {
        binding.vm = viewModel
    }

    private fun initBackBtnClickListener() {
        binding.btnTodoDetailBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun initDeleteBtnClickListener() {
        binding.btnTodoDetailDelete.setOnSingleClickListener {
            viewModel.deleteTodoFromServer()
        }
    }

    private fun initModBtnClickListener() {
        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    finish()
                }
            }
        binding.btnTodoDetailMod.setOnSingleClickListener {
            startForResult.launch(
                TodoChangeActivity.createIntent(
                    this, viewModel.tripId, viewModel.todoId
                )
            )
        }
    }

    private fun getIntentData() {
        viewModel.tripId = intent.getLongExtra(EXTRA_TRIP_ID, 0)
        viewModel.todoId = intent.getLongExtra(EXTRA_TODO_ID, 0)
        viewModel.isPublic = intent.getBooleanExtra(EXTRA_IS_PUBLIC, true)
    }

    private fun initAllocatorListAdapter() {
        if (viewModel.isPublic) {
            _adapter = TripAllocatorAdapter()
            binding.rvOurTodoDetailPerson.adapter = adapter
        }
    }

    private fun resetDetailData() {
        viewModel.getTodoDetailFromServer()
    }

    private fun observeTodoDetailState() {
        viewModel.todoDetailState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Loading -> return@onEach

                is UiState.Success -> {
                    if (viewModel.isPublic) {
                        adapter.submitList(state.data.allocators)
                    } else {
                        with(binding) {
                            rvOurTodoDetailPerson.visibility = View.INVISIBLE
                            layoutMyTodoCreatePerson.visibility = View.VISIBLE
                        }
                    }

                    with(binding) {
                        tvTodoCreateMemoTitle.isVisible = true
                        etTodoCreateMemo.isVisible = true
                        tvTodoMemoCounter.isVisible = true
                    }

                    if (state.data.memo.isBlank()) {
                        with(binding) {
                            etTodoCreateMemo.background =
                                drawableOf(R.drawable.shape_rect_4_gray200_line)
                            etTodoCreateMemo.text = stringOf(R.string.my_todo_create_tv_memo_hint)
                            etTodoCreateMemo.setTextColor(colorOf(R.color.gray_200))
                            tvTodoMemoCounter.isVisible = false
                        }
                    }
                }

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
        private const val EXTRA_TRIP_ID = "EXTRA_TRIP_ID"
        private const val EXTRA_TODO_ID = "EXTRA_TODO_ID"
        private const val EXTRA_IS_PUBLIC = "EXTRA_IS_PUBLIC"

        @JvmStatic
        fun createIntent(
            context: Context,
            tripId: Long,
            todoId: Long,
            isPublic: Boolean,
        ): Intent = Intent(context, TodoDetailActivity::class.java).apply {
            putExtra(EXTRA_TRIP_ID, tripId)
            putExtra(EXTRA_TODO_ID, todoId)
            putExtra(EXTRA_IS_PUBLIC, isPublic)
        }
    }
}