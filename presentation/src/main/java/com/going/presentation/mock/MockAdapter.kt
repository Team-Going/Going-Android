package com.going.presentation.mock

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.response.MockFollowerModel
import com.going.presentation.databinding.ItemMockBinding
import com.going.ui.extension.ItemDiffCallback

class MockAdapter : ListAdapter<MockFollowerModel, MockViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MockViewHolder {
        val binding: ItemMockBinding =
            ItemMockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MockViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    fun addList(newItems: List<MockFollowerModel>) {
        val currentItems = currentList.toMutableList()
        currentItems.addAll(newItems)
        submitList(currentItems)
    }

    companion object {
        private val diffUtil = ItemDiffCallback<MockFollowerModel>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}