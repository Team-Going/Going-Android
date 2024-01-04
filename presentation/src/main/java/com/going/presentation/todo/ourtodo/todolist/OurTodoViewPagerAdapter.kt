package com.going.presentation.todo.ourtodo.todolist

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OurTodoViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OurTodoUncompleteFragment()
            else -> OurTodoCompleteFragment()
        }
    }
}