package com.going.presentation.designsystem.textview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.going.presentation.databinding.ViewChartTextviewBinding
import com.going.ui.extension.setBulletPoint

class ChartTextView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private val binding: ViewChartTextviewBinding

    init {
        binding = ViewChartTextviewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true,
        )
    }

    fun setTitle(text: String) {
        binding.tvChartTitle.text = text
    }

    fun setFirstDescription(text: String) {
        binding.tvChartFirstDescription.text = text.setBulletPoint()
    }

    fun setSecondDescription(text: String) {
        binding.tvChartSecondDescription.text = text.setBulletPoint()
    }

    fun setThirdDescription(text: String) {
        binding.tvChartThirdDescription.text = text.setBulletPoint()
    }
}
