package com.going.presentation.designsystem.edittext

import android.content.Context
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

class EmojiCounterEditText(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private val binding: ViewEmojiCounterEdittextBinding

    private var maxLen: Int = 0
    private var canBlankError: Boolean = false
    lateinit var overWarning: String
    lateinit var blankWarning: String

    val editText
        get() = binding.etEmojiCounterEtContent

    private val editTextStateMap = mapOf(
        EditTextState.SUCCESS to Triple(R.color.gray_700, R.drawable.shape_rect_4_gray700_line, ""),
        EditTextState.EMPTY to Triple(R.color.gray_200, R.drawable.shape_rect_4_gray200_line, ""),
        EditTextState.BLANK to Triple(R.color.red_500, R.drawable.shape_rect_4_red500_line, blankWarning),
        EditTextState.BLANK to Triple(R.color.red_500, R.drawable.shape_rect_4_red500_line, overWarning),
    )

    var state: EditTextState = EditTextState.EMPTY
        set(value) {
            field = value
            editTextStateMap[field]?.let { setEditTextState(it) }
        }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmojiCounterEditText)

        binding = ViewEmojiCounterEdittextBinding.inflate(
            LayoutInflater.from(context),
            this,
            true,
        )

        binding.tvEmojiCounterEtTitle.text =
            typedArray.getString(R.styleable.EmojiCounterEditText_title)
        binding.etEmojiCounterEtContent.hint =
            typedArray.getString(R.styleable.EmojiCounterEditText_hint)
        canBlankError = typedArray.getBoolean(R.styleable.EmojiCounterEditText_canBlankError, false)
        binding.etEmojiCounterEtContent.minLines = typedArray.getInt(R.styleable.EmojiCounterEditText_minLines, 1)

        typedArray.recycle()

        binding.tvEmojiCounterEtNameCounter.text = context.getString(R.string.counter, 0, maxLen)

        checkTextAvailable()
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
