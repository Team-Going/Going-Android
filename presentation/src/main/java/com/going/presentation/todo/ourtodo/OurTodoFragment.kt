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
import com.going.presentation.mock.MockAdapter
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

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
    }

}