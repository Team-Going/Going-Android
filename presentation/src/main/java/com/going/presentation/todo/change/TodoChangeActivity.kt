package com.going.presentation.todo.change

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTodoChangeBinding
import com.going.presentation.todo.create.TodoCreateActivity.Companion.MAX_MEMO_LEN
import com.going.presentation.todo.create.TodoCreateActivity.Companion.MAX_TODO_LEN
import com.going.presentation.todo.create.TripParticipantAdapter
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

    private var _adapter: TripParticipantAdapter? = null
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
            // TODO 수정
        }
    }

    private fun initBackBtnListener() {
        binding.btnTodoCreateBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun getTodoInfo() {
        viewModel.todoId = intent.getLongExtra(EXTRA_TODO_ID, 0)
        viewModel.getTodoDetailFromServer()
    }

    private fun observeTodoDetailState() {
        viewModel.todoDetailState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Loading -> return@onEach

                is UiState.Success -> {
                    if (!state.data) {
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

    private fun initOurTodoNameListAdapter() {
        _adapter = TripParticipantAdapter(false) { position ->
            // TODO 어댑터 수정
            viewModel.allocatorModelList[position].also { it.isAllocated = !it.isAllocated }
            viewModel.checkIsFinishAvailable()
        }
        binding.rvOurTodoCreatePerson.adapter = adapter
        adapter.submitList(viewModel.allocatorModelList)
    }

    private fun setMyTodoParticipant() {
        with(binding) {
            rvOurTodoCreatePerson.visibility = View.INVISIBLE
            layoutMyTodoCreatePerson.visibility = View.VISIBLE
        }
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
        private const val EXTRA_TODO_ID = "EXTRA_TODO_ID"

        @JvmStatic
        fun createIntent(
            context: Context,
            todoId: Long
        ): Intent = Intent(context, TodoChangeActivity::class.java).apply {
            putExtra(EXTRA_TODO_ID, todoId)
        }
    }
}
