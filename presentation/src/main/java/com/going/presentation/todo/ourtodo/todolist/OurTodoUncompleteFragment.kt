package com.going.presentation.todo.ourtodo.todolist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.FragmentOurTodoUncompleteBinding
import com.going.presentation.todo.ourtodo.OurTodoViewModel
import com.going.presentation.todo.ourtodo.OurTodoViewModel.Companion.OUR_TODO
import com.going.presentation.todo.ourtodo.OurTodoViewModel.Companion.UNCOMPLETE
import com.going.presentation.todo.detail.PublicDetailActivity
import com.going.presentation.todo.detail.PublicDetailActivity.Companion.EXTRA_TODO_ID
import com.going.ui.base.BaseFragment
import com.going.ui.extension.UiState
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class OurTodoUncompleteFragment() :
    BaseFragment<FragmentOurTodoUncompleteBinding>(R.layout.fragment_our_todo_uncomplete) {

    private var _adapter: OurTodoListAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by activityViewModels<OurTodoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapterWithClickListener()
        setTodoList()
        observeTodoListState()
    }

    override fun onResume() {
        super.onResume()

        setTodoList()
    }

    private fun initAdapterWithClickListener() {
        _adapter = OurTodoListAdapter(
            false
        ) { todoId ->
            Intent(activity, PublicDetailActivity::class.java).apply {
                putExtra(EXTRA_TODO_ID, todoId)
                startActivity(this)
            }
        }
        binding.rvOurTodoUncomplete.adapter = adapter
    }

    private fun setTodoList() {
        viewModel.getTodoListFromServer(OUR_TODO, UNCOMPLETE)
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
        binding.rvOurTodoUncomplete.isVisible = !isEmpty
        binding.layoutOurTodoUncompleteEmpty.isVisible = isEmpty
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }

}