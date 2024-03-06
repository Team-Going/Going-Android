package com.going.presentation.profile.trip.tripprofiletag.changetag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.going.domain.entity.ProfilePreferenceData
import com.going.presentation.databinding.ItemPreferenceTagBinding
import com.going.ui.util.ItemDiffCallback

class ChangeTagAdapter : ListAdapter<ProfilePreferenceData, ChangeTagViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangeTagViewHolder {
        val inflater by lazy { LayoutInflater.from(parent.context) }
        val binding: ItemPreferenceTagBinding =
            ItemPreferenceTagBinding.inflate(inflater, parent, false)
        return ChangeTagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChangeTagViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun getItemCount() = currentList.size

    companion object {
        private val diffUtil = ItemDiffCallback<ProfilePreferenceData>(
            onItemsTheSame = { old, new -> old.number == new.number },
            onContentsTheSame = { old, new -> old == new },
        )
    }
}