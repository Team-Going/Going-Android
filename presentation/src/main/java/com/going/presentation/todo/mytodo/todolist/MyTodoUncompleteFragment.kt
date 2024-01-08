package com.going.presentation.todo.mytodo.todolist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.going.presentation.R
import com.going.presentation.databinding.FragmentMyTodoUncompleteBinding
import com.going.presentation.todo.mytodo.MyTodoViewModel
import com.going.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

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
        setTodoList()
    }

    private fun initAdapterWithClickListener() {
        _adapter = MyTodoListAdapter(false, { position ->
            adapter.removeItem(position)
            adapter.notifyDataSetChanged()
            viewModel.decreaseTodoCount()
        }, { })
        binding.rvMyTodoUncomplete.adapter = adapter
    }

    private fun setTodoList() {
        adapter.setItemList(viewModel.mockUncompleteTodoList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }

}