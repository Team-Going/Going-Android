package com.going.presentation.todo.ourtodo

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.going.presentation.R
import com.going.presentation.databinding.FragmentOurTodoBinding
import com.going.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OurTodoFragment() : BaseFragment<FragmentOurTodoBinding>(R.layout.fragment_our_todo) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDateTextColor()
        setProgressBarStatus()
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

}