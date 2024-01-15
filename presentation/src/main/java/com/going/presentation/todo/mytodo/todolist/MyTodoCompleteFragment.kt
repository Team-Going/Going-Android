package com.going.presentation.todo.mytodo.todolist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.FragmentMyTodoCompleteBinding
import com.going.presentation.todo.detail.PrivateDetailActivity
import com.going.presentation.todo.detail.PublicDetailActivity
import com.going.presentation.todo.detail.PublicDetailActivity.Companion.EXTRA_TODO_ID
import com.going.presentation.todo.mytodo.MyTodoViewModel
import com.going.presentation.todo.mytodo.MyTodoViewModel.Companion.COMPLETE
import com.going.presentation.todo.mytodo.MyTodoViewModel.Companion.MY_TODO
import com.going.ui.base.BaseFragment
import com.going.ui.extension.EnumUiState
import com.going.ui.extension.UiState
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MyTodoCompleteFragment() :
    BaseFragment<FragmentMyTodoCompleteBinding>(R.layout.fragment_my_todo_complete) {

    private var _adapter: MyTodoListAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by activityViewModels<MyTodoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapterWithClickListener()
        observeTodoListState()
        observeTodoRedoState()
    }

    override fun onResume() {
        super.onResume()

        setTodoList()
    }

    private fun initAdapterWithClickListener() {
        _adapter = MyTodoListAdapter(true,
            { },
            { todoId ->
                viewModel.getToRedoTodoFromServer(todoId)
            },
            { todoModel ->
                if (todoModel.allocators.size <= 1) {
                    startDetailActivity(PrivateDetailActivity::class.java, todoModel.todoId)
                } else {
                    startDetailActivity(PublicDetailActivity::class.java, todoModel.todoId)
                }
            })
        binding.rvMyTodoComplete.adapter = adapter
    }

    private fun startDetailActivity(targetActivity: Class<*>, todoId: Long) {
        Intent(activity, targetActivity).apply {
            putExtra(EXTRA_TODO_ID, todoId)
            activity?.startActivity(this)
        }
    }

    private fun setTodoList() {
        viewModel.getCompleteTodoListFromServer(MY_TODO, COMPLETE)
    }

    private fun observeTodoListState() {
        viewModel.todoCompleteListState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    setLayoutEmpty(false)
                    adapter.submitList(state.data)
                }

                is UiState.Failure -> {
                    setLayoutEmpty(true)
                    toast(getString(R.string.server_error))
                }

                is UiState.Loading -> return@onEach

                is UiState.Empty -> setLayoutEmpty(true)
            }
        }.launchIn(lifecycleScope)
    }

    private fun setLayoutEmpty(isEmpty: Boolean) {
        binding.rvMyTodoComplete.isVisible = !isEmpty
        binding.layoutMyTodoCompleteEmpty.isVisible = isEmpty
    }

    private fun observeTodoRedoState() {
        viewModel.todoRedoState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                EnumUiState.LOADING -> return@onEach

                EnumUiState.SUCCESS -> {
                    setTodoList()
                    viewModel.increaseTodoCount()
                }

                EnumUiState.FAILURE -> toast(getString(R.string.server_error))

                EnumUiState.EMPTY -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }

}