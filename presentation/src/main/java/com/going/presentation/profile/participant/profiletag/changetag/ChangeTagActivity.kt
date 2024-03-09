package com.going.presentation.profile.participant.profiletag.changetag

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.going.domain.entity.ProfilePreferenceData
import com.going.presentation.R
import com.going.presentation.databinding.ActivityChangeTagBinding
import com.going.presentation.entertrip.preferencetag.PreferenceTagDecoration
import com.going.ui.base.BaseActivity
import com.going.ui.extension.colorOf
import com.going.ui.extension.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeTagActivity :
    BaseActivity<ActivityChangeTagBinding>(R.layout.activity_change_tag) {

    private val tagViewModel by viewModels<ChangeTagViewModel>()

    private var _adapter: ChangeTagAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val preferenceAnswers = MutableList(5) { 0 }

    private var styleA = 0
    private var styleB = 0
    private var styleC = 0
    private var styleD = 0
    private var styleE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()
        initPreferenceList()
        initItemDecoration()
        initBackClickListener()
    }

    private fun initAdapter() {
        _adapter = ChangeTagAdapter(::preferenceTagClickListener)
        binding.rvChangeTag.adapter = adapter
    }

    private fun initPreferenceList() {
        if (intent != null) {
            styleA = intent.getIntExtra(STYLE_A, 0)
            styleB = intent.getIntExtra(STYLE_B, 0)
            styleC = intent.getIntExtra(STYLE_C, 0)
            styleD = intent.getIntExtra(STYLE_D, 0)
            styleE = intent.getIntExtra(STYLE_E, 0)

            adapter.submitList(
                tagViewModel.initPreferenceData(
                    styleA,
                    styleB,
                    styleC,
                    styleD,
                    styleE
                )
            )

            preferenceAnswers[0] = styleA
            preferenceAnswers[1] = styleB
            preferenceAnswers[2] = styleC
            preferenceAnswers[3] = styleD
            preferenceAnswers[4] = styleE
        }
    }

    private fun preferenceTagClickListener(item: ProfilePreferenceData, checkedIndex: Int) {
        setButtonValid()
        preferenceAnswers[item.number.toInt() - 1] = checkedIndex
    }

    private fun setButtonValid() {
        binding.btnChangeStart.isEnabled = true
        binding.btnChangeStart.setTextColor(
            colorOf(R.color.white_000)
        )
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

    companion object {
        private const val STYLE_A = "styleA"
        private const val STYLE_B = "styleB"
        private const val STYLE_C = "styleC"
        private const val STYLE_D = "styleD"
        private const val STYLE_E = "styleE"

        @JvmStatic
        fun createIntent(
            context: Context,
            styleA: Int,
            styleB: Int,
            styleC: Int,
            styleD: Int,
            styleE: Int
        ): Intent = Intent(context, ChangeTagActivity::class.java).apply {
            putExtra(STYLE_A, styleA)
            putExtra(STYLE_B, styleB)
            putExtra(STYLE_C, styleC)
            putExtra(STYLE_D, styleD)
            putExtra(STYLE_E, styleE)
        }
    }

}