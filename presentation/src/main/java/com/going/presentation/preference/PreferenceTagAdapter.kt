package com.going.presentation.preference

import android.view.LayoutInflater
import android.view.ViewGroup
import com.going.domain.entity.PreferenceData
import androidx.recyclerview.widget.ListAdapter
import com.going.presentation.databinding.ItemPreferenceTagBinding
import com.going.ui.extension.ItemDiffCallback

class PreferenceTagAdapter : ListAdapter<PreferenceData, PreferenceTagViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreferenceTagViewHolder {
        val binding: ItemPreferenceTagBinding =
            ItemPreferenceTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreferenceTagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PreferenceTagViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun getItemCount() = currentList.size

    companion object {
        private val diffUtil = ItemDiffCallback<PreferenceData>(
            onItemsTheSame = { old, new -> old.number == new.number },
            onContentsTheSame = { old, new -> old == new },
        )
    }

}