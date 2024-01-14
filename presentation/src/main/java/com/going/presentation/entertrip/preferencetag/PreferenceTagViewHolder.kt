package com.going.presentation.entertrip.preferencetag

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
            tvPreferenceNumber.text = item.number
            tvPreferenceQuestion.text = item.question
            tvPreferenceTag1.text = item.leftPrefer
            tvPreferenceTag3.text = item.rightPrefer

            rgPreferenceTag.setOnCheckedChangeListener { _, checkedId ->
                val selectedButtonIdList = listOf(
                    R.id.rb_preference_1,
                    R.id.rb_preference_2,
                    R.id.rb_preference_3,
                    R.id.rb_preference_4,
                    R.id.rb_preference_5
                )

                val checkedIndex = selectedButtonIdList.indexOf(checkedId)
                if (checkedIndex != -1) {
                    listener.onPreferenceSelected(item, checkedIndex)
                }
            }
        }
    }
}
