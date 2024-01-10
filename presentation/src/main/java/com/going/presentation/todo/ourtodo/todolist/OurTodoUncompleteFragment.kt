package com.going.presentation.todo.ourtodo.todolist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.going.presentation.R
import com.going.presentation.databinding.FragmentOurTodoUncompleteBinding
import com.going.presentation.todo.ourtodo.OurTodoViewModel
import com.going.presentation.todo.ourtodo.detail.OurTodoDetailActivity
import com.going.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

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
    }

    private fun initAdapterWithClickListener() {
        _adapter = OurTodoListAdapter(
            false
        ) { todoId ->
            Intent(activity, OurTodoDetailActivity::class.java).apply {
                putExtra(OurTodoDetailActivity.EXTRA_TODO_ID, todoId)
                startActivity(this)
            }
        }
        binding.rvOurTodoUncomplete.adapter = adapter
    }

    private fun setTodoList() {
        adapter.submitList(viewModel.mockCompleteTodoList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }

}