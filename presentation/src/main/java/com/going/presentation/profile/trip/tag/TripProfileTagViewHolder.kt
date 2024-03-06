package com.going.presentation.profile.trip.tag

import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.PreferenceData
import com.going.presentation.databinding.ItemPreferenceTagBinding

class TripProfileTagViewHolder(
    val binding: ItemPreferenceTagBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: PreferenceData) {
        binding.run {
            tvPreferenceNumber.text = item.number
            tvPreferenceQuestion.text = item.question
            tvPreferenceTag1.text = item.leftPrefer
            tvPreferenceTag3.text = item.rightPrefer
        }
    }

}