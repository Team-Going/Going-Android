package com.going.presentation.profile.trip.tripprofiletag.profiletag

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.PreferenceData
import com.going.presentation.databinding.ItemPreferenceTagBinding
import com.going.ui.util.ItemDiffCallback

class PreferenceTagAdapter(
    context: Context
) : ListAdapter<PreferenceData, PreferenceTagViewHolder>(diffUtil) {

    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreferenceTagViewHolder {
        val binding: ItemPreferenceTagBinding =
            ItemPreferenceTagBinding.inflate(inflater, parent, false)
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