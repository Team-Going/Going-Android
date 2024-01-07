package com.going.presentation.todo.ourtodo.todolist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.going.domain.entity.response.TodoModel
import com.going.presentation.R
import com.going.presentation.databinding.FragmentOurTodoUncompleteBinding
import com.going.presentation.todo.ourtodo.OurTodoFriendAdapter
import com.going.presentation.todo.ourtodo.OurTodoViewModel
import com.going.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OurTodoUncompleteFragment() :
    BaseFragment<FragmentOurTodoUncompleteBinding>(R.layout.fragment_our_todo_uncomplete) {

    private var _adapter: OurTodoListAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by activityViewModels<OurTodoViewModel>()

    val mockTodoList: List<TodoModel> = listOf(
        TodoModel(0,"숙소 예약하기", "2024-01-12", listOf("김상호", "박동민")),
        TodoModel(1,"기차 왕복 예약하기", "2024-01-14", listOf("조세연")),
        TodoModel(2,"와사비맛 아몬드 먹기", "2024-01-15", listOf("이유빈", "김상호")),
        TodoModel(3,"커피 사기", "2024-01-15", listOf("이유빈"))
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
    }

    private fun setRecyclerView() {
        _adapter = OurTodoListAdapter()
        binding.rvOurTodoUncomplete.adapter = adapter
        adapter.submitList(mockTodoList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }

}