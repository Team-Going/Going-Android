package com.going.presentation.todo.ourtodo

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
import com.going.presentation.databinding.FragmentOurTodoBinding
import com.going.presentation.todo.ourtodo.create.OurTodoCreateActivity
import com.going.presentation.todo.ourtodo.todolist.OurTodoViewPagerAdapter
import com.going.ui.base.BaseFragment
import com.going.ui.extension.UiState
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class OurTodoFragment() : BaseFragment<FragmentOurTodoBinding>(R.layout.fragment_our_todo) {

    private val tabTextList = listOf(TAB_UNCOMPLETE, TAB_COMPLETE)

    private var _adapter: OurTodoFriendAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by activityViewModels<OurTodoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initAddTodoBtnListener()
        initItemDecoration()
        setMyTripInfo()
        setTabLayout()
        setViewPager()
        observeOurTripInfoState()
    }

    private fun initAdapter() {
        _adapter = OurTodoFriendAdapter()
        binding.rvOurTripFriend.adapter = adapter
    }

    private fun initAddTodoBtnListener() {
        binding.btnOurTodoAddTodo.setOnSingleClickListener {
            Intent(activity, OurTodoCreateActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun initItemDecoration() {
        val itemDeco = OurTodoDecoration(requireContext())
        binding.rvOurTripFriend.addItemDecoration(itemDeco)
    }

    private fun setMyTripInfo() {
        // TODO: tripId
        val tripId : Long = 1
        viewModel.getOurTripInfoFromServer(tripId)
    }

    private fun setDateTextColor() {
        binding.tvOurTodoTitleDown.apply {
            text = SpannableStringBuilder(text).apply {
                setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(
                            requireContext(), R.color.red_500
                        )
                    ), 6, length - 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }

    private fun setTabLayout() {
        binding.tabOurTodo.apply {
            for (tabName in tabTextList) {
                val tab = this.newTab()
                tab.text = tabName
                this.addTab(tab)
            }
        }
    }

    private fun setViewPager() {
        binding.vpOurTodo.adapter = OurTodoViewPagerAdapter(this)
        binding.vpOurTodo.isUserInputEnabled = false
        TabLayoutMediator(binding.tabOurTodo, binding.vpOurTodo) { tab, pos ->
            tab.text = tabTextList[pos]
        }.attach()
    }

    private fun observeOurTripInfoState() {
        viewModel.ourTripInfoState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Loading -> return@onEach

                is UiState.Success -> {
                    binding.run {
                        tvOurTodoTitleUp.text = state.data.title
                        // TODO: 날짜 분기처리
                        tvOurTodoTitleDown.text = "여행일까지 %s일 남았어요!".format(state.data.day)
                        setDateTextColor()
                        tvOurTodoTitleDate.text = "%s - %s".format(convertDate(state.data.startDate), convertDate(state.data.endDate))
                        progressBarOurTodo.progress = state.data.progress
                        tvOurTripInfoPercent.text = state.data.progress.toString() + "%"
                        adapter.submitList(state.data.participants)
                    }
                }

                is UiState.Failure -> toast(getString(R.string.server_error))

                is UiState.Empty -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun convertDate(date: String): String {
        val splitDate = date.split(".")
        return "${splitDate[1]}월 ${splitDate[2]}일"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }

    companion object {
        const val TAB_UNCOMPLETE = "미완료 todo"
        const val TAB_COMPLETE = "완료 todo"
    }

}