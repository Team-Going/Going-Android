package com.going.presentation.designsystem.edittext

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.going.presentation.R
import com.going.presentation.databinding.ViewEmojiCounterEdittextBinding
import com.going.ui.extension.colorOf
import com.going.ui.extension.getGraphemeLength
import com.going.ui.extension.setOnSingleClickListener

class EmojiCounterEditText(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private val binding: ViewEmojiCounterEdittextBinding

    private var maxLen: Int = 0
    private var canBlankError: Boolean = false
    lateinit var overWarning: String
    var blankWarning: String = ""

    private val editTextStateMap by lazy {
        mapOf(
            EditTextState.SUCCESS to Triple(
                R.color.gray_700,
                R.drawable.shape_rect_4_gray700_line,
                ""
            ),
            EditTextState.EMPTY to Triple(
                R.color.gray_200,
                R.drawable.shape_rect_4_gray200_line,
                ""
            ),
            EditTextState.BLANK to Triple(
                R.color.red_500,
                R.drawable.shape_rect_4_red500_line,
                blankWarning
            ),
            EditTextState.OVER to Triple(
                R.color.red_500,
                R.drawable.shape_rect_4_red500_line,
                overWarning
            ),
        )
    }

    val editText
        get() = binding.etEmojiCounterEtContent

    var state: EditTextState = EditTextState.EMPTY
        set(value) {
            field = value

            binding.btnDeleteText.isVisible = value != EditTextState.EMPTY
            editTextStateMap[field]?.let { setEditTextState(it) }
        }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmojiCounterEditText)
        binding = ViewEmojiCounterEdittextBinding.inflate(
            LayoutInflater.from(context),
            this,
            true,
        )

        initDeleteBtnClickListener()
        setBindingContent(typedArray)

        typedArray.recycle()

        checkTextAvailable()
    }

    private fun initDeleteBtnClickListener() = with(binding) {
        btnDeleteText.setOnSingleClickListener {
            etEmojiCounterEtContent.text = null
        }
    }

    private fun setBindingContent(typedArray: TypedArray) {
        with(binding) {
            btnDeleteText.isVisible = state != EditTextState.EMPTY
            tvEmojiCounterEtTitle.text =
                typedArray.getString(R.styleable.EmojiCounterEditText_title)
            etEmojiCounterEtContent.hint =
                typedArray.getString(R.styleable.EmojiCounterEditText_hint)
            etEmojiCounterEtContent.minLines =
                typedArray.getInt(R.styleable.EmojiCounterEditText_minLines, 1)
            tvEmojiCounterEtNameCounter.text = context.getString(R.string.counter, 0, maxLen)
        }
        canBlankError = typedArray.getBoolean(R.styleable.EmojiCounterEditText_canBlankError, false)
    }

    private fun checkTextAvailable() {
        binding.etEmojiCounterEtContent.doAfterTextChanged { text ->
            val len = text.toString().getGraphemeLength()

            state = when {
                text.toString().isBlank() && len != 0 && canBlankError -> EditTextState.BLANK
                len > maxLen -> EditTextState.OVER
                len > 0 -> EditTextState.SUCCESS
                else -> EditTextState.EMPTY
            }

            binding.tvEmojiCounterEtNameCounter.text =
                context.getString(R.string.counter, len, maxLen)
        }
    }

    fun setMaxLen(len: Int) {
        maxLen = len
        binding.tvEmojiCounterEtNameCounter.text = context.getString(R.string.counter, 0, maxLen)
    }

    private fun setEditTextState(info: Triple<Int, Int, String>) {
        val color = info.first
        val background = info.second
        val text = info.third

        with(binding) {
            tvEmojiCounterEtWarningMessage.isVisible = color == R.color.red_500
            tvEmojiCounterEtNameCounter.setTextColor(context.colorOf(color))
            etEmojiCounterEtContent.background = ResourcesCompat.getDrawable(
                this@EmojiCounterEditText.resources,
                background,
                context.theme,
            )
            tvEmojiCounterEtWarningMessage.text = text
        }
    }
}
