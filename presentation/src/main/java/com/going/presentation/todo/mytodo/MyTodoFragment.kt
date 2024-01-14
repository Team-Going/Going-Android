package com.going.presentation.todo.mytodo

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.presentation.R
import com.going.presentation.databinding.FragmentMyTodoBinding
import com.going.presentation.todo.mytodo.create.MyTodoCreateActivity
import com.going.presentation.todo.mytodo.todolist.MyTodoViewPagerAdapter
import com.going.ui.base.BaseFragment
import com.going.ui.extension.UiState
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MyTodoFragment() : BaseFragment<FragmentMyTodoBinding>(R.layout.fragment_my_todo) {

    private val tabTextList = listOf(TAB_UNCOMPLETE, TAB_COMPLETE)

    private val viewModel by activityViewModels<MyTodoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAddTodoListener()
        setMyTripInfo()
        setTabLayout()
        setViewPager()
        setTodoCountText()
        observeMyTripInfoState()
        observeTotalUncompletedTodoCount()
    }

    private fun initAddTodoListener() {
        binding.btnMyTodoAddTodo.setOnSingleClickListener {
            Intent(activity, MyTodoCreateActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun setMyTripInfo() {
        // TODO: tripId
        val tripId: Long = 1
        viewModel.getMyTripInfoFromServer(tripId)
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
        binding.vpMyTodo.isUserInputEnabled = false
        TabLayoutMediator(binding.tabMyTodo, binding.vpMyTodo) { tab, pos ->
            tab.text = tabTextList[pos]
        }.attach()
    }

    private fun setTodoCountText() {
        setTodoCount(viewModel.totalUncompletedTodoCount.value)
    }

    private fun observeMyTripInfoState() {
        viewModel.myTripInfoState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Loading -> return@onEach

                is UiState.Success -> binding.tvMyTodoTitleUp.text = state.data.name

                is UiState.Failure -> toast(getString(R.string.server_error))

                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun observeTotalUncompletedTodoCount() {
        viewModel.totalUncompletedTodoCount.flowWithLifecycle(lifecycle).onEach { count ->
            setTodoCount(count)
        }.launchIn(lifecycleScope)
    }

    private fun setTodoCount(count: Int) {
        binding.tvMyTodoTitleDown.apply {
            text = SpannableStringBuilder(
                getString(R.string.my_todo_tv_title_down).format(count)
            ).apply {
                setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(
                            requireContext(), R.color.red_500
                        )
                    ), length - 3, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }

    companion object {
        const val TAB_UNCOMPLETE = "해야 해요"
        const val TAB_COMPLETE = "완료했어요"
    }

}