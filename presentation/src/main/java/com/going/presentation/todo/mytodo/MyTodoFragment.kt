package com.going.presentation.todo.mytodo

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.going.presentation.R
import com.going.presentation.databinding.FragmentMyTodoBinding
import com.going.presentation.todo.mytodo.todolist.MyTodoViewPagerAdapter
import com.going.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyTodoFragment() : BaseFragment<FragmentMyTodoBinding>(R.layout.fragment_my_todo) {

    private val tabTextList = listOf(TAB_UNCOMPLETE, TAB_COMPLETE)

    private val viewModel by activityViewModels<MyTodoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTodoCountText()
        setTabLayout()
        setViewPager()
    }

    private fun setTodoCountText() {
        binding.tvMyTodoTitleDown.apply {
            text = SpannableStringBuilder(
                getString(R.string.my_todo_tv_title_down).format(viewModel.mockUncompleteTodoList.size)
            ).apply {
                setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(
                            requireContext(), R.color.red_500
                        )
                    ), 3, length - 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }

    private fun setTabLayout() {
        binding.tabMyTodo.apply {
            for (tabName in tabTextList) {
                val tab = this.newTab()
                tab.text = tabName
                this.addTab(tab)
            }
        }
    }

    private fun setViewPager() {
        binding.vpMyTodo.adapter = MyTodoViewPagerAdapter(this)
        TabLayoutMediator(binding.tabMyTodo, binding.vpMyTodo) { tab, pos ->
            tab.text = tabTextList[pos]
        }.attach()
    }

    companion object {
        const val TAB_UNCOMPLETE = "미완료 todo"
        const val TAB_COMPLETE = "완료 todo"
    }

}