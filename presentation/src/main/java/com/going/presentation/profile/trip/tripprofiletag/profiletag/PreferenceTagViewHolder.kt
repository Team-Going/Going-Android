package com.going.presentation.profile.trip.tripprofiletag.profiletag

import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.ProfilePreferenceData
import com.going.presentation.databinding.ItemPreferenceTagBinding

class PreferenceTagViewHolder(
    val binding: ItemPreferenceTagBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: ProfilePreferenceData) {
        binding.run {
            tvPreferenceNumber.text = item.number
            tvPreferenceQuestion.text = item.question
            tvPreferenceTag1.text = item.leftPrefer
            tvPreferenceTag3.text = item.rightPrefer

            val preferenceList =
                listOf(rbPreference1, rbPreference2, rbPreference3, rbPreference4, rbPreference5)

            preferenceList.forEachIndexed { index, radioButton ->
                radioButton.isChecked = index + 1 == item.preferenceIndex
                radioButton.isClickable = false
            }
        }
    }

}