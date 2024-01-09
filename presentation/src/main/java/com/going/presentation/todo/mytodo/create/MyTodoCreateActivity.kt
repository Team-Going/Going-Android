package com.going.presentation.todo.mytodo.create

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.going.presentation.R
import com.going.presentation.databinding.ActivityMyTodoCreateBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class MyTodoCreateActivity :
    BaseActivity<ActivityMyTodoCreateBinding>(R.layout.activity_my_todo_create) {

    private val viewModel by viewModels<MyTodoCreateViewModel>()

    private var myTodoCreateBottomSheet: MyTodoCreateBottomSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initTodoFocusListener()
        initMemoFocusListener()
        initDateClickListener()
        initFinishBtnListener()
        initBackBtnListener()
        observeTextLength()
        observeMemoLength()
        observeDateEmpty()
    }

    private fun initViewModel() {
        binding.vm = viewModel
    }

    private fun initTodoFocusListener() {
        binding.etMyTodoCreateTodo.setOnFocusChangeListener { _, hasFocus ->
            setColors(
                hasFocus,
                viewModel.nowTodoLength.value ?: 0,
                binding.tvMyTodoTodoCounter,
            ) { background ->
                binding.etMyTodoCreateTodo.background = setBackgroundColor(background)
            }
        }
    }

    private fun initMemoFocusListener() {
        binding.etMyTodoCreateMemo.setOnFocusChangeListener { _, hasFocus ->
            setColors(
                hasFocus,
                viewModel.nowMemoLength.value ?: 0,
                binding.tvMyTodoMemoCounter,
            ) { background ->
                binding.etMyTodoCreateMemo.background = setBackgroundColor(background)
            }
        }
    }

    private fun initDateClickListener() {
        binding.etMyTodoCreateDate.setOnSingleClickListener {
            myTodoCreateBottomSheet = MyTodoCreateBottomSheet()
            myTodoCreateBottomSheet?.show(supportFragmentManager, DATE_BOTTOM_SHEET)
        }
    }

    private fun initFinishBtnListener() {
        binding.btnMyTodoMemoFinish.setOnSingleClickListener {
            // 서버통신 진행
            finish()
        }
    }

    private fun initBackBtnListener() {
        binding.btnMyTodoCreateBack.setOnSingleClickListener {
            finish()
        }
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
            setColors(
                false,
                viewModel.nowTodoLength.value ?: 0,
                binding.tvMyTodoTodoCounter,
            ) { background ->
                binding.etMyTodoCreateTodo.background = setBackgroundColor(background)
            }
        }
    }

    private fun observeMemoLength() {
        viewModel.nowMemoLength.observe(this) { length ->
            val maxMemoLen = viewModel.getMaxMemoLen()
            if (length > maxMemoLen) {
                binding.etMyTodoCreateTodo.apply {
                    setText(text?.subSequence(0, maxMemoLen))
                    setSelection(maxMemoLen)
                }
            }
            setColors(
                false,
                viewModel.nowMemoLength.value ?: 0,
                binding.tvMyTodoMemoCounter,
            ) { background ->
                binding.etMyTodoCreateMemo.background = setBackgroundColor(background)
            }
        }
    }

    private fun observeDateEmpty() {
        viewModel.endDate.observe(this) { text ->
            if (text.isEmpty()) {
                binding.etMyTodoCreateDate.setBackgroundResource(R.drawable.shape_rect_4_gray200_line)
            } else {
                binding.etMyTodoCreateDate.setBackgroundResource(R.drawable.shape_rect_4_gray700_line)
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

    private fun setBackgroundColor(background: Int): Drawable? {
        return ResourcesCompat.getDrawable(
            this.resources,
            background,
            theme,
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        if (myTodoCreateBottomSheet?.isAdded == true) myTodoCreateBottomSheet?.dismiss()
    }

    companion object {
        private const val DATE_BOTTOM_SHEET = "DATE_BOTTOM_SHEET"
    }
}