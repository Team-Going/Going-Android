package com.going.presentation.todo.mytodo.todolist

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.FragmentMyTodoUncompleteBinding
import com.going.presentation.todo.detail.TodoDetailActivity
import com.going.presentation.todo.mytodo.MyTodoFragment
import com.going.presentation.todo.mytodo.MyTodoViewModel
import com.going.presentation.todo.mytodo.MyTodoViewModel.Companion.MY_TODO
import com.going.presentation.todo.mytodo.MyTodoViewModel.Companion.UNCOMPLETE
import com.going.ui.base.BaseFragment
import com.going.ui.extension.toast
import com.going.ui.state.EnumUiState
import com.going.ui.state.UiState
import com.going.ui.util.RvItemLastDecoration
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
                TodoDetailActivity.createIntent(
                    requireContext(), viewModel.tripId, todoModel.todoId, !todoModel.secret
                ).apply { startActivity(this) }
            })
        binding.rvMyTodoUncomplete.adapter = adapter
    }

    private fun initItemDecoration() {
        val itemDeco = RvItemLastDecoration(requireContext(), 0, 0, 0, 30)
        binding.rvMyTodoUncomplete.addItemDecoration(itemDeco)
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
        (requireParentFragment() as MyTodoFragment).showEmptyView(isEmpty)
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