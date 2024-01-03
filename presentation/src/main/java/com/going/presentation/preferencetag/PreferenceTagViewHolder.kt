package com.going.presentation.preferencetag

import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.going.domain.entity.PreferenceData
import com.going.presentation.R
import com.going.presentation.databinding.ItemPreferenceTagBinding

class PreferenceTagViewHolder(
    val binding: ItemPreferenceTagBinding,
    private val listener: PreferenceTagAdapter.OnPreferenceSelectedListener
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: PreferenceData) {
        binding.run {
            tvPreferenceNumber.text = item.number.toString()
            tvPreferenceLeft.text = item.leftPrefer
            tvPreferenceRight.text = item.rightPrefer

            btnPreference1.setOnClickListener {
                listener.onPreferenceSelected(item)
            }
            btnPreference2.setOnClickListener {
                listener.onPreferenceSelected(item)
            }
            btnPreference3.setOnClickListener{
                listener.onPreferenceSelected(item)
            }
            btnPreference4.setOnClickListener{
                listener.onPreferenceSelected(item)
            }
            btnPreference5.setOnClickListener{
                listener.onPreferenceSelected(item)
            }

        }
    }
}
