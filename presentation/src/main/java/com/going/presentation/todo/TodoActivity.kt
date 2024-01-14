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

    var tripId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBnvItemIconTintList()
        initBnvItemSelectedListener()
        getTripId()
    }

    private fun initBnvItemIconTintList() {
        binding.bnvTodo.itemIconTintList = null
        binding.bnvTodo.selectedItemId = R.id.menu_our_todo
    }

    private fun initBnvItemSelectedListener() {
        supportFragmentManager.findFragmentById(R.id.fcv_todo) ?: navigateTo<OurTodoFragment>(tripId)

        binding.bnvTodo.setOnItemSelectedListener { menu ->
            if (binding.bnvTodo.selectedItemId == menu.itemId) {
                return@setOnItemSelectedListener false
            }
            when (menu.itemId) {
                R.id.menu_our_todo -> navigateTo<OurTodoFragment>(tripId)

                R.id.menu_my_todo -> navigateTo<MyTodoFragment>(tripId)

                else -> return@setOnItemSelectedListener false
            }
            true
        }
    }

    private inline fun <reified T : Fragment> navigateTo(id: Long) {
        val bundle = Bundle().apply {
            putLong(EXTRA_TRIP_ID, id)
        }
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_todo, T::class.java.canonicalName, bundle)
        }
    }

    private fun getTripId() {
        if (intent != null) {
            tripId = intent.getLongExtra(EXTRA_TRIP_ID, 0)
        }
    }

    companion object {
        const val EXTRA_TRIP_ID = "TRIP_ID"
    }

}