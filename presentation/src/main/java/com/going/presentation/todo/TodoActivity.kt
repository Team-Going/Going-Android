package com.going.presentation.todo

import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTodoBinding
import com.going.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoActivity() : BaseActivity<ActivityTodoBinding>(R.layout.activity_todo) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}