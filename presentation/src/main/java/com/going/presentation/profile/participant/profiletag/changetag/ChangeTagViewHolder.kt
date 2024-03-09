package com.going.presentation.profile.participant.profiletag.changetag

import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.ProfilePreferenceData
import com.going.presentation.databinding.ItemPreferenceTagBinding
import com.going.ui.extension.setOnSingleClickListener

class ChangeTagViewHolder(
    val binding: ItemPreferenceTagBinding,
    private val itemSelect: (ProfilePreferenceData, Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: ProfilePreferenceData) {
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
                viewPreferenceRadio1,
                viewPreferenceRadio2,
                viewPreferenceRadio3,
                viewPreferenceRadio4,
                viewPreferenceRadio5
            )

            selectedButtonList.forEachIndexed { index, radioButton ->
                radioButton.isChecked = index == item.preferenceIndex

                radioButton.setOnSingleClickListener {
                    if (radioButton.isChecked) itemSelect(item, index)
                }
            }

            selectedViewList.forEachIndexed { index, view ->
                view.setOnSingleClickListener {
                    selectedButtonList[index].isChecked = true
                    itemSelect(item, index)
                }
            }
        }
    }

}