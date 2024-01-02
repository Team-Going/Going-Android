package com.going.presentation.preference

import android.view.ViewGroup
import com.going.domain.entity.PreferenceData
import androidx.recyclerview.widget.ListAdapter

import com.going.ui.extension.ItemDiffCallback

class PreferenceTagAdapter : ListAdapter<PreferenceData, PreferenceTagViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreferenceTagViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PreferenceTagViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        private val diffUtil = ItemDiffCallback<PreferenceData>(
            onItemsTheSame = { old, new -> old.number == new.number },
            onContentsTheSame = { old, new -> old == new },
        )
    }

}