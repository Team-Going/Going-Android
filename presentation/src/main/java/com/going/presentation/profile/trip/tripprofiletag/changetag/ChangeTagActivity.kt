package com.going.presentation.profile.trip.tripprofiletag.changetag

import android.os.Bundle
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityChangeTagBinding
import com.going.presentation.entertrip.preferencetag.PreferenceTagDecoration
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeTagActivity :
    BaseActivity<ActivityChangeTagBinding>(R.layout.activity_change_tag) {

    private val viewModel by viewModels<ChangeTagViewModel>()

    private var _adapter: ChangeTagAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()
        initItemDecoration()
        initBackClickListener()
    }

    private fun initAdapter() {
        _adapter = ChangeTagAdapter()
        binding.rvChangeTag.adapter = adapter
        adapter.submitList(viewModel.preferenceTagList)
    }

    private fun initItemDecoration() {
        val itemDeco = PreferenceTagDecoration(this)
        binding.rvChangeTag.addItemDecoration(itemDeco)
    }

    private fun initBackClickListener() {
        binding.btnChangeTagBack.setOnSingleClickListener {
            finish()
        }
    }

}