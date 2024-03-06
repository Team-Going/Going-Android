package com.going.presentation.todo.change

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTodoChangeBinding
import com.going.presentation.todo.create.TodoCreateActivity.Companion.MAX_MEMO_LEN
import com.going.presentation.todo.create.TodoCreateActivity.Companion.MAX_TODO_LEN
import com.going.presentation.todo.detail.TodoDetailActivity
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import com.going.ui.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TodoChangeActivity : BaseActivity<ActivityTodoChangeBinding>(R.layout.activity_todo_change) {

    private val viewModel by viewModels<TodoChangeViewModel>()

    private var _adapter: TodoAllocatorAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private var todoModDateBottomSheet: TodoModDateBottomSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initDateClickBtnListener()
        initFinishBtnListener()
        initBackBtnListener()
        getTodoInfo()
        observeTodoDetailState()
        observePatchTodoState()
        setBackPressedBtnListener()
        setEtTodoNameArguments()
        setEtTodoMemoArguments()
        observeNameTextChanged()
        observeMemoTextChanged()
    }

    private fun initViewModel() {
        binding.vm = viewModel
    }

    private fun initDateClickBtnListener() {
        binding.etTodoCreateDate.setOnSingleClickListener {
            todoModDateBottomSheet = TodoModDateBottomSheet()
            todoModDateBottomSheet?.show(supportFragmentManager, DATE_BOTTOM_SHEET)
        }
    }

    private fun initFinishBtnListener() {
        binding.btnTodoMemoFinish.setOnSingleClickListener {
            viewModel.patchTodoToServer()
        }
    }

    private fun initBackBtnListener() {
        binding.btnTodoCreateBack.setOnSingleClickListener {
            navigateBackToDetailActivity()
        }
    }

    private fun getTodoInfo() {
        viewModel.tripId = intent.getLongExtra(EXTRA_TRIP_ID, 0)
        viewModel.todoId = intent.getLongExtra(EXTRA_TODO_ID, 0)
        viewModel.getTodoDetailFromServer()
    }

    private fun observeTodoDetailState() {
        viewModel.todoDetailState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Loading -> return@onEach

                is UiState.Success -> {
                    with(binding) {
                        etTodoCreateTodo.editText.setText(viewModel.todo.value)
                        etTodoCreateMemo.editText.setText(viewModel.memo.value)
                    }
                    if (!state.data.secret) {
                        initOurTodoNameListAdapter()
                        return@onEach
                    }
                    setMyTodoParticipant()
                }

                is UiState.Failure -> toast(getString(R.string.server_error))

                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun observePatchTodoState() {
        viewModel.todoPatchState.flowWithLifecycle(lifecycle).onEach { result ->
            if (result) {
                toast(getString(R.string.todo_change_toast_success))
                finish()
                return@onEach
            }
            toast(getString(R.string.todo_change_toast_failure))
        }.launchIn(lifecycleScope)
    }

    private fun initOurTodoNameListAdapter() {
        _adapter = TodoAllocatorAdapter { position ->
            viewModel.allocatorModelList[position].also { it.isAllocated = !it.isAllocated }
            viewModel.checkIsFinishAvailable()
        }
        binding.rvOurTodoCreatePerson.adapter = adapter
        adapter.submitList(viewModel.allocatorModelList)
        binding.etTodoCreateMemo.isVisible = true
    }

    private fun setMyTodoParticipant() {
        with(binding) {
            rvOurTodoCreatePerson.visibility = View.INVISIBLE
            layoutMyTodoCreatePerson.visibility = View.VISIBLE
        }
    }

    private fun setBackPressedBtnListener() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
               navigateBackToDetailActivity()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun navigateBackToDetailActivity() {
        TodoDetailActivity.createIntent(
            this, viewModel.tripId, viewModel.todoId, !viewModel.isSecret
        ).apply { startActivity(this) }
        finish()
    }

    private fun setEtTodoNameArguments() {
        with(binding.etTodoCreateTodo) {
            setMaxLen(MAX_TODO_LEN)
            overWarning = getString(R.string.todo_over_error)
            blankWarning = getString(R.string.name_blank_error)
        }
    }

    private fun setEtTodoMemoArguments() {
        with(binding.etTodoCreateMemo) {
            setMaxLen(MAX_MEMO_LEN)
            overWarning = getString(R.string.memo_over_error)
        }
    }

    private fun observeNameTextChanged() {
        binding.etTodoCreateTodo.editText.doAfterTextChanged { text ->
            viewModel.setNameState(text.toString(), binding.etTodoCreateTodo.state)
        }
    }

    private fun observeMemoTextChanged() {
        binding.etTodoCreateMemo.editText.doAfterTextChanged { text ->
            viewModel.setMemoState(text.toString(), binding.etTodoCreateMemo.state)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
        if (todoModDateBottomSheet?.isAdded == true) todoModDateBottomSheet?.dismiss()
    }

    companion object {
        private const val DATE_BOTTOM_SHEET = "DATE_BOTTOM_SHEET"
        private const val EXTRA_TRIP_ID = "EXTRA_TRIP_ID"
        private const val EXTRA_TODO_ID = "EXTRA_TODO_ID"

        @JvmStatic
        fun createIntent(
            context: Context,
            tripId: Long,
            todoId: Long
        ): Intent = Intent(context, TodoChangeActivity::class.java).apply {
            putExtra(EXTRA_TRIP_ID, tripId)
            putExtra(EXTRA_TODO_ID, todoId)
        }
    }
}
