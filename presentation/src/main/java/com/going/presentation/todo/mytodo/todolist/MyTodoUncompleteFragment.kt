package com.going.presentation.todo.mytodo.todolist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.going.presentation.R
import com.going.presentation.databinding.FragmentMyTodoUncompleteBinding
import com.going.presentation.todo.mytodo.MyTodoViewModel
import com.going.presentation.todo.ourtodo.todolist.OurTodoListAdapter
import com.going.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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
            Timber.tag("sangho").d("delete position : ${position}")
            Timber.tag("sangho").d("delete id : ${adapter.currentList[position].todoId}")
            adapter.removeItem(position)
            adapter.notifyDataSetChanged()
            Timber.tag("sangho").d("list update : ${adapter.currentList}")
        },{ })
        binding.rvMyTodoUncomplete.adapter = adapter
    }

    private fun setTodoList() {
        adapter.submitList(viewModel.mockUncompleteTodoList)
        Timber.tag("sangho").d("list set : ${adapter.currentList}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }

}