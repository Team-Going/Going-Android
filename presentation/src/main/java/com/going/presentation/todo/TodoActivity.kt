package com.going.presentation.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTodoBinding
import com.going.presentation.todo.mytodo.MyTodoFragment
import com.going.presentation.todo.ourtodo.OurTodoFragment
import com.going.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoActivity() : BaseActivity<ActivityTodoBinding>(R.layout.activity_todo) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBnvItemIconTintList()
        initBnvItemSelectedListener()
    }

    private fun initBnvItemIconTintList() {
        binding.bnvTodo.itemIconTintList = null
        binding.bnvTodo.selectedItemId = R.id.menu_our_todo
    }

    private fun initBnvItemSelectedListener() {
        supportFragmentManager.findFragmentById(R.id.fcv_todo) ?: navigateTo<OurTodoFragment>()

        binding.bnvTodo.setOnItemSelectedListener { menu ->
            if (binding.bnvTodo.selectedItemId == menu.itemId) {
                return@setOnItemSelectedListener false
            }
            when (menu.itemId) {
                R.id.menu_our_todo -> navigateTo<OurTodoFragment>()

                R.id.menu_my_todo -> navigateTo<MyTodoFragment>()

                else -> return@setOnItemSelectedListener false
            }
            true
        }
    }

    private inline fun <reified T : Fragment> navigateTo() {
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_todo, T::class.java.canonicalName)
        }
    }

}