package com.going.presentation.preferencetag

import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.PreferenceData
import com.going.presentation.databinding.ItemPreferenceTagBinding

class PreferenceTagViewHolder(val binding: ItemPreferenceTagBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: PreferenceData) {
        binding.run {
            tvPreferenceNumber.text = item.number.toString()
            tvPreferenceLeft.text = item.leftPrefer
            tvPreferenceRight.text = item.rightPrefer
        }
    }

}