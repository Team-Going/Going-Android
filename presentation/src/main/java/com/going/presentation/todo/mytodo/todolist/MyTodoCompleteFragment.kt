package com.going.presentation.todo.mytodo.todolist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.going.presentation.R
import com.going.presentation.databinding.FragmentMyTodoCompleteBinding
import com.going.presentation.todo.mytodo.MyTodoViewModel
import com.going.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

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
        setTodoList()
    }

    private fun initAdapterWithClickListener() {
        _adapter = MyTodoListAdapter(true, { position ->
            adapter.removeItem(position)
            adapter.notifyDataSetChanged()
        }, { })
        binding.rvMyTodoComplete.adapter = adapter
    }

    private fun setTodoList() {
        adapter.setItemList(viewModel.mockCompleteTodoList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }

}