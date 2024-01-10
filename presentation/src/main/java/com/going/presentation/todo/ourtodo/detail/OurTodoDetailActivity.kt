package com.going.presentation.todo.ourtodo.detail

import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityOurTodoDetailBinding
import com.going.ui.base.BaseActivity

class OurTodoDetailActivity :
    BaseActivity<ActivityOurTodoDetailBinding>(R.layout.activity_our_todo_detail) {

    private val viewModel by viewModels<OurTodoDetailViewModel>()

    companion object {
        const val EXTRA_TODO_ID = "EXTRA_TODO_ID"
    }
}