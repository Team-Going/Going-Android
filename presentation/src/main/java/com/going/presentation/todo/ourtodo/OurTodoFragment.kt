package com.going.presentation.todo.ourtodo

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.going.presentation.R
import com.going.presentation.databinding.FragmentOurTodoBinding
import com.going.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OurTodoFragment() : BaseFragment<FragmentOurTodoBinding>(R.layout.fragment_our_todo) {

    private var _adapter: OurTodoAdapter? = null
    private val adapter
        get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by activityViewModels<OurTodoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        setDateTextColor()
        setProgressBarStatus()
        setTabLayout()
    }

    private fun initAdapter() {
        _adapter = OurTodoAdapter()
        binding.rvOurTripFriend.adapter = adapter
        adapter.submitList(viewModel.mockParticipantsList)
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

    private fun setProgressBarStatus() {
        binding.progressBarOurTodo.progress = 40
    }

    private fun setTabLayout() {
        binding.tabOurTodo.apply {
            for (tabName in listOf(TAB_UNCOMPLETE, TAB_COMPLETE)) {
                val tab = this.newTab()
                tab.text = tabName
                this.addTab(tab)
            }
        }
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