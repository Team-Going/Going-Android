package com.going.presentation.designsystem.textview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.going.presentation.R
import com.going.presentation.databinding.ViewChartTextviewBinding

class ChartTextView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private val binding: ViewChartTextviewBinding

    init {
        binding = ViewChartTextviewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true,
        )

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChartTextView)

        binding.tvChartTitle.text = typedArray.getString(R.styleable.ChartTextView_title)
        binding.tvChartFirstDescription.text = typedArray.getString(R.styleable.ChartTextView_first)
        binding.tvChartSecondDescription.text = typedArray.getString(R.styleable.ChartTextView_second)
        binding.tvChartThirdDescription.text = typedArray.getString(R.styleable.ChartTextView_third)

        typedArray.recycle()
    }
}
