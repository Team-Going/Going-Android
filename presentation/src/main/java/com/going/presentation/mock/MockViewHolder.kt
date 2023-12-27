package com.going.presentation.mock

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.going.domain.entity.response.MockFollowerModel
import com.going.presentation.databinding.ItemMockBinding

class MockViewHolder(val binding: ItemMockBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: MockFollowerModel) {
        binding.run {
            tvFollowerName.text = item.name
            tvFollowerEmail.text = item.email
            ivFollowerImage.load(item.image) {
                transformations(RoundedCornersTransformation(10.0F))
            }
        }
    }

}