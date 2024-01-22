package com.going.presentation.designsystem.edittext

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.going.presentation.R
import com.going.presentation.databinding.ViewEmojiCounterEdittextBinding

class EmojiCounterEditText(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {
    private var binding: ViewEmojiCounterEdittextBinding = ViewEmojiCounterEdittextBinding.inflate(
        LayoutInflater.from(context),
        this,
        true,
    )

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmojiCounterEditText)

        binding.tvEmojiCounterEtTitle.text =
            typedArray.getString(R.styleable.EmojiCounterEditText_title)
        binding.etEmojiCounterEtContent.hint =
            typedArray.getString(R.styleable.EmojiCounterEditText_hint)
        binding.tvEmojiCounterEtWarningMessage.text =
            typedArray.getString(R.styleable.EmojiCounterEditText_warning)
        binding.tvEmojiCounterEtNameCounter.text =
            typedArray.getString(R.styleable.EmojiCounterEditText_count)

        typedArray.recycle()
    }
}
