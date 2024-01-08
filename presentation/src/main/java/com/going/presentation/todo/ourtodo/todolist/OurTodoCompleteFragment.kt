package com.going.presentation.todo.ourtodo.todolist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.going.presentation.R
import com.going.presentation.databinding.FragmentOurTodoCompleteBinding
import com.going.presentation.todo.ourtodo.OurTodoViewModel
import com.going.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OurTodoCompleteFragment() :
    BaseFragment<FragmentOurTodoCompleteBinding>(R.layout.fragment_our_todo_complete) {

    private var _adapter: OurTodoListAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by activityViewModels<OurTodoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTodoListAdapter()
    }

    private fun initTodoListAdapter() {
        _adapter = OurTodoListAdapter(true)
        binding.rvOurTodoComplete.adapter = adapter
        adapter.submitList(viewModel.mockCompleteTodoList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }

}