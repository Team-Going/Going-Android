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

            rgPreferenceTag.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.rb_preference_1 -> {
                        listener.onPreferenceSelected(item)
                    }

                    R.id.rb_preference_2 -> {
                        listener.onPreferenceSelected(item)
                    }

                    R.id.rb_preference_3 -> {
                        listener.onPreferenceSelected(item)
                    }

                    R.id.rb_preference_4 -> {
                        listener.onPreferenceSelected(item)
                    }

                    R.id.rb_preference_5 -> {
                        listener.onPreferenceSelected(item)
                    }
                }
            }
        }
    }
}
