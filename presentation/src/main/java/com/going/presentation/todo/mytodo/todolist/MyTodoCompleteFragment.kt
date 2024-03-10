package com.going.presentation.todo.mytodo.todolist

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.FragmentMyTodoCompleteBinding
import com.going.presentation.todo.detail.TodoDetailActivity
import com.going.presentation.todo.mytodo.MyTodoFragment
import com.going.presentation.todo.mytodo.MyTodoViewModel
import com.going.presentation.todo.mytodo.MyTodoViewModel.Companion.COMPLETE
import com.going.presentation.todo.mytodo.MyTodoViewModel.Companion.MY_TODO
import com.going.ui.base.BaseFragment
import com.going.ui.extension.toast
import com.going.ui.state.EnumUiState
import com.going.ui.state.UiState
import com.going.ui.util.RvItemLastDecoration
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
        initItemDecoration()
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
                TodoDetailActivity.createIntent(
                    requireContext(), viewModel.tripId, todoModel.todoId, !todoModel.secret
                ).apply { startActivity(this) }
            })
        binding.rvMyTodoComplete.adapter = adapter
    }

    private fun initItemDecoration() {
        val itemDeco = RvItemLastDecoration(requireContext(), 0, 0, 0, 30)
        binding.rvMyTodoComplete.addItemDecoration(itemDeco)
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
        (requireParentFragment() as MyTodoFragment).showEmptyView(isEmpty)
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