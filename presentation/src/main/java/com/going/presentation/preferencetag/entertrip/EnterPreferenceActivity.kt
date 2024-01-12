package com.going.presentation.preferencetag.entertrip

import android.os.Bundle
import androidx.activity.viewModels
import com.going.domain.entity.PreferenceData
import com.going.presentation.R
import com.going.presentation.databinding.ActivityEnterPreferenceBinding
import com.going.presentation.preferencetag.PreferenceTagAdapter
import com.going.presentation.preferencetag.PreferenceTagDecoration
import com.going.presentation.preferencetag.PreferenceTagViewModel
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener

class EnterPreferenceActivity :
    BaseActivity<ActivityEnterPreferenceBinding>(R.layout.activity_enter_preference),
    PreferenceTagAdapter.OnPreferenceSelectedListener {

    private var _adapter: PreferenceTagAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val viewModel by viewModels<PreferenceTagViewModel>()

    private val preferenceAnswers = MutableList(5) { Int.MAX_VALUE }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()
        initItemDecoration()
        initBackClickListener()
        isButtonValid()

    }

    private fun initAdapter() {
        _adapter = PreferenceTagAdapter(this, this)
        binding.rvPreferenceTag.adapter = adapter
        adapter.submitList(viewModel.preferenceTagList)
    }

    private fun initItemDecoration() {
        val itemDeco = PreferenceTagDecoration(this)
        binding.rvPreferenceTag.addItemDecoration(itemDeco)
    }

    private fun initBackClickListener() {
        binding.btnPreferenceBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun isButtonValid(){

    }

    override fun onPreferenceSelected(item: PreferenceData, checkList: Int) {
        preferenceAnswers[item.number.toInt() - 1] = checkList
    }

    override fun onDestroy() {
        super.onDestroy()
        _adapter = null
    }
}