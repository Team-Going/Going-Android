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

            rgPreferenceTag.setOnCheckedChangeListener { _, checkedId ->
                val selectedButtonIdList = listOf(
                    R.id.rb_preference_1,
                    R.id.rb_preference_2,
                    R.id.rb_preference_3,
                    R.id.rb_preference_4,
                    R.id.rb_preference_5
                )

                if (checkedId in selectedButtonIdList) {
                    listener.onPreferenceSelected(item)
                }
            }
        }
    }
}
