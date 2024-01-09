package com.going.presentation.todo.mytodo.create

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.going.presentation.R
import com.going.presentation.databinding.ActivityMyTodoCreateBinding
import com.going.ui.base.BaseActivity

class MyTodoCreateActivity :
    BaseActivity<ActivityMyTodoCreateBinding>(R.layout.activity_my_todo_create) {

    private val viewModel by viewModels<MyTodoCreateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeTextLength()
        binding.vm = viewModel

        binding.etMyTodoCreateTodo.setOnFocusChangeListener { _, hasFocus ->
            setColors(
                hasFocus,
                viewModel.nowTodoLength.value ?: 0,
                binding.tvMyTodoTodoCounter,
            ) { background ->
                binding.etMyTodoCreateTodo.background = ResourcesCompat.getDrawable(
                    this.resources,
                    background,
                    theme,
                )
            }
        }

        viewModel.nowTodoLength.observe(this) {
            setColors(
                false,
                viewModel.nowTodoLength.value ?: 0,
                binding.tvMyTodoTodoCounter,
            ) { background ->
                binding.etMyTodoCreateTodo.background = ResourcesCompat.getDrawable(
                    this.resources,
                    background,
                    theme,
                )
            }
        }
    }

    private fun setColors(
        hasFocus: Boolean,
        length: Int,
        counter: TextView,
        setBackground: (Int) -> Unit,
    ) {
        val (color, background) = when {
            hasFocus || viewModel.nowTodoLength.value != 0 -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
            length == 0 -> R.color.gray_200 to R.drawable.shape_rect_4_gray200_line
            else -> R.color.gray_700 to R.drawable.shape_rect_4_gray700_line
        }

        setCounterColor(counter, color)
        setBackground(background)
    }

    private fun setCounterColor(counter: TextView, color: Int) {
        counter.setTextColor(getColor(color))
    }
    private fun observeTextLength() {
        viewModel.nowTodoLength.observe(this) { length ->
            val maxTodoLen = viewModel.getMaxTodoLen()

            if (length > maxTodoLen) {
                binding.etMyTodoCreateTodo.apply {
                    setText(text?.subSequence(0, maxTodoLen))
                    setSelection(maxTodoLen)
                }
            }
        }
    }
}