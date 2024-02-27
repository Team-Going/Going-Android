package com.going.presentation.todo.change

import android.os.Bundle
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTodoChangeBinding
import com.going.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoChangeActivity : BaseActivity<ActivityTodoChangeBinding>(R.layout.activity_todo_change) {

    private val viewModel by viewModels<TodoChangeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}