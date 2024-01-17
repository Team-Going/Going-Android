package com.going.presentation.entertrip.preferencetag

import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.PreferenceData
import com.going.presentation.databinding.ItemPreferenceTagBinding

class PreferenceTagViewHolder(
    val binding: ItemPreferenceTagBinding,
    private val listener: PreferenceTagAdapter.OnPreferenceSelectedListener
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: PreferenceData) {
        binding.run {
            tvPreferenceNumber.text = item.number
            tvPreferenceQuestion.text = item.question
            tvPreferenceTag1.text = item.leftPrefer
            tvPreferenceTag3.text = item.rightPrefer

            val selectedButtonList = listOf(
                rbPreference1,
                rbPreference2,
                rbPreference3,
                rbPreference4,
                rbPreference5
            )

            val selectedViewList = listOf(
                viewRadio1,
                viewRadio2,
                viewRadio3,
                viewRadio4,
                viewRadio5
            )

            selectedButtonList.forEachIndexed { index, radioButton ->
                radioButton.setOnClickListener {
                    if (radioButton.isChecked) {
                        listener.onPreferenceSelected(item, index)
                    }
                }
            }

            selectedViewList.forEachIndexed { index, view ->
                view.setOnClickListener {
                    selectedButtonList[index].isChecked = true
                    listener.onPreferenceSelected(item, index)
                }
            }
        }
    }
}