package com.going.presentation.preferencetag

import android.os.Bundle
import androidx.activity.viewModels
import com.going.domain.entity.PreferenceData
import com.going.presentation.R
import com.going.presentation.databinding.ActivityPreferenceTagBinding
import com.going.ui.base.BaseActivity

class PreferenceTagActivity :
    BaseActivity<ActivityPreferenceTagBinding>(R.layout.activity_preference_tag),
    PreferenceTagAdapter.OnPreferenceSelectedListener {

    private var _adapter: PreferenceTagAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by viewModels<PreferenceTagViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() {
        _adapter = PreferenceTagAdapter(this, this)
        binding.rvPreferenceTag.adapter = adapter
        adapter.submitList(viewModel.preferenceTagList)
    }

    override fun onPreferenceSelected(preference: PreferenceData) {
        // 선택된 취향 태그 처리
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }
}