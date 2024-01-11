package com.going.presentation.todo.mytodo.todolist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.FragmentMyTodoUncompleteBinding
import com.going.presentation.todo.mytodo.MyTodoViewModel
import com.going.presentation.todo.mytodo.MyTodoViewModel.Companion.MY_TODO
import com.going.presentation.todo.mytodo.MyTodoViewModel.Companion.UNCOMPLETE
import com.going.presentation.todo.mytodo.detail.MyTodoDetailActivity
import com.going.presentation.todo.mytodo.detail.MyTodoDetailActivity.Companion.EXTRA_TODO_ID
import com.going.ui.base.BaseFragment
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
        observeTodoListState()
    }

    override fun onResume() {
        super.onResume()
        setTodoList()
    }

    private fun initAdapterWithClickListener() {
        _adapter = MyTodoListAdapter(false,
            { position ->
                adapter.removeItem(position)
                adapter.notifyDataSetChanged()
                viewModel.decreaseTodoCount()
            },
            { },
            { todoId ->
                Intent(activity, MyTodoDetailActivity::class.java).apply {
                    putExtra(EXTRA_TODO_ID, todoId)
                    startActivity(this)
                }
            })
        binding.rvMyTodoUncomplete.adapter = adapter
    }

    private fun setTodoList() {
        // 추후 tripId 설정
        val tripId: Long = 1
        viewModel.getUncompleteTodoListFromServer(tripId, MY_TODO, UNCOMPLETE)
    }

    private fun observeTodoListState() {
        viewModel.todoUncompleteListState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> adapter.submitList(state.data)

                is UiState.Failure -> toast(getString(R.string.server_error))

                is UiState.Loading -> return@onEach

                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }

}