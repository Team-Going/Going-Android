package com.going.presentation.todo.mytodo.todolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.FragmentMyTodoUncompleteBinding
import com.going.presentation.todo.TodoDecoration
import com.going.presentation.todo.detail.PrivateDetailActivity
import com.going.presentation.todo.detail.PublicDetailActivity
import com.going.presentation.todo.detail.PublicDetailActivity.Companion.EXTRA_TODO_ID
import com.going.presentation.todo.mytodo.MyTodoFragment
import com.going.presentation.todo.mytodo.MyTodoViewModel
import com.going.presentation.todo.mytodo.MyTodoViewModel.Companion.MY_TODO
import com.going.presentation.todo.mytodo.MyTodoViewModel.Companion.UNCOMPLETE
import com.going.ui.base.BaseFragment
import com.going.ui.extension.EnumUiState
import com.going.ui.extension.UiState
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MyTodoUncompleteFragment() :
    BaseFragment<FragmentMyTodoUncompleteBinding>(R.layout.fragment_my_todo_uncomplete) {

    private var _adapter: MyTodoListAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by activityViewModels<MyTodoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapterWithClickListener()
        initItemDecoration()
        observeTodoListState()
        observeTodoFinishState()
    }

    override fun onResume() {
        super.onResume()

        setTodoList()
    }

    private fun initAdapterWithClickListener() {
        _adapter = MyTodoListAdapter(false,
            { todoId ->
                viewModel.getToFinishTodoFromServer(todoId)
            },
            { },
            { todoModel ->
                if (todoModel.allocators.size <= 1) {
                    startDetailActivity(
                        activity,
                        PrivateDetailActivity::class.java,
                        todoModel.todoId
                    )
                } else {
                    startDetailActivity(
                        activity,
                        PublicDetailActivity::class.java,
                        todoModel.todoId
                    )
                }
            })
        binding.rvMyTodoUncomplete.adapter = adapter
    }

    private fun initItemDecoration() {
        val itemDeco = TodoDecoration(requireContext(),0,0,0,30)
        binding.rvMyTodoUncomplete.addItemDecoration(itemDeco)
    }

    private fun startDetailActivity(activity: Activity?, targetActivity: Class<*>, todoId: Long) {
        Intent(activity, targetActivity).apply {
            putExtra(EXTRA_TODO_ID, todoId)
            activity?.startActivity(this)
        }
    }

    private fun setTodoList() {
        viewModel.getUncompleteTodoListFromServer(MY_TODO, UNCOMPLETE)
    }

    private fun observeTodoListState() {
        viewModel.todoUncompleteListState.flowWithLifecycle(lifecycle).onEach { state ->
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
        binding.rvMyTodoUncomplete.isVisible = !isEmpty
        binding.layoutMyTodoUncompleteEmpty.isVisible = isEmpty
        (parentFragment as MyTodoFragment).setAppbarDragAvailable(!isEmpty)
    }

    private fun observeTodoFinishState() {
        viewModel.todoFinishState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                EnumUiState.LOADING -> return@onEach

                EnumUiState.SUCCESS -> setTodoList()

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