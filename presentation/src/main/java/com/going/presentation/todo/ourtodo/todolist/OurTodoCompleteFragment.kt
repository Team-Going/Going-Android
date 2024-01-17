package com.going.presentation.todo.ourtodo.todolist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.FragmentOurTodoCompleteBinding
import com.going.presentation.todo.TodoDecoration
import com.going.presentation.todo.ourtodo.OurTodoViewModel
import com.going.presentation.todo.ourtodo.OurTodoViewModel.Companion.COMPLETE
import com.going.presentation.todo.ourtodo.OurTodoViewModel.Companion.OUR_TODO
import com.going.presentation.todo.detail.PublicDetailActivity
import com.going.presentation.todo.detail.PublicDetailActivity.Companion.EXTRA_TODO_ID
import com.going.presentation.todo.mytodo.MyTodoFragment
import com.going.presentation.todo.ourtodo.OurTodoFragment
import com.going.ui.base.BaseFragment
import com.going.ui.extension.UiState
import com.going.ui.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class OurTodoCompleteFragment() :
    BaseFragment<FragmentOurTodoCompleteBinding>(R.layout.fragment_our_todo_complete) {

    private var _adapter: OurTodoListAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by activityViewModels<OurTodoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapterWithClickListener()
        initItemDecoration()
        observeTodoListState()
    }

    override fun onResume() {
        super.onResume()

        setTodoList()
    }

    private fun initAdapterWithClickListener() {
        _adapter = OurTodoListAdapter(
            true
        ) { todoId ->
            Intent(activity, PublicDetailActivity::class.java).apply {
                putExtra(EXTRA_TODO_ID, todoId)
                startActivity(this)
            }
        }
        binding.rvOurTodoComplete.adapter = adapter
    }

    private fun initItemDecoration() {
        val itemDeco = TodoDecoration(requireContext(),0,0,0,30)
        binding.rvOurTodoComplete.addItemDecoration(itemDeco)
    }

    private fun setTodoList() {
        viewModel.getTodoListFromServer(OUR_TODO, COMPLETE)
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
        binding.rvOurTodoComplete.isVisible = !isEmpty
        (requireParentFragment() as OurTodoFragment).showEmptyView(isEmpty)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }

}