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
import com.going.ui.extension.getGraphemeLength

class EmojiCounterEditText(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private val binding: ViewEmojiCounterEdittextBinding

    private val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmojiCounterEditText)
    private var maxLen: Int = 0
    private var canBlankError: Boolean = false
    lateinit var overWarning: String
    lateinit var blankWarning: String

    val editText
        get() = binding.etEmojiCounterEtContent

    var state: EditTextState = EditTextState.Empty
        set(value) {
            field = value
            when (field) {
                EditTextState.Success -> setSuccessState()
                EditTextState.Empty -> setEmptyState()
                EditTextState.Blank -> setWarningMessage(blankWarning)
                EditTextState.OVER -> setWarningMessage(overWarning)
            }
        }

    init {
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

        typedArray.recycle()

        binding.tvEmojiCounterEtNameCounter.text = context.getString(R.string.counter, 0, maxLen)

        checkTextAvailable()
    }

    private fun checkTextAvailable() {
        binding.etEmojiCounterEtContent.doAfterTextChanged { text ->
            val len = text.toString().getGraphemeLength()

            state =
                if (text.toString().isBlank() && len != 0 && canBlankError) {
                    EditTextState.Blank
                } else if (len > maxLen) {
                    EditTextState.OVER
                } else if (len > 0) {
                    EditTextState.Success
                } else {
                    EditTextState.Empty
                }

            binding.tvEmojiCounterEtNameCounter.text =
                context.getString(R.string.counter, len, maxLen)
        }
    }

    fun setMaxLen(len: Int) {
        maxLen = len
        binding.tvEmojiCounterEtNameCounter.text = context.getString(R.string.counter, 0, maxLen)
    }

    private fun setSuccessState() {
        binding.tvEmojiCounterEtWarningMessage.isVisible = false
        binding.tvEmojiCounterEtNameCounter.setTextColor(context.getColor(R.color.gray_700))
        binding.etEmojiCounterEtContent.background = ResourcesCompat.getDrawable(
            this.resources,
            R.drawable.shape_rect_4_gray700_line,
            context.theme,
        )
    }

    private fun setEmptyState() {
        binding.tvEmojiCounterEtWarningMessage.isVisible = false
        binding.tvEmojiCounterEtNameCounter.setTextColor(context.getColor(R.color.gray_200))
        binding.etEmojiCounterEtContent.background = ResourcesCompat.getDrawable(
            this.resources,
            R.drawable.shape_rect_4_gray200_line,
            context.theme,
        )
    }

    private fun setWarningMessage(text: String?) {
        binding.tvEmojiCounterEtWarningMessage.isVisible = true
        binding.tvEmojiCounterEtWarningMessage.text = text
        binding.tvEmojiCounterEtNameCounter.setTextColor(context.getColor(R.color.red_500))
        binding.etEmojiCounterEtContent.background = ResourcesCompat.getDrawable(
            this.resources,
            R.drawable.shape_rect_4_red500_line,
            context.theme,
        )
    }
}