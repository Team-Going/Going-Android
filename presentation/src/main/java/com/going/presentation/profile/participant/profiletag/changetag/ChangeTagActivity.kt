package com.going.presentation.profile.participant.profiletag.changetag

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.going.domain.entity.ProfilePreferenceData
import com.going.presentation.R
import com.going.presentation.databinding.ActivityChangeTagBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.colorOf
import com.going.ui.extension.setOnSingleClickListener
import com.going.ui.extension.toast
import com.going.ui.util.RVItemFirstLastDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ChangeTagActivity :
    BaseActivity<ActivityChangeTagBinding>(R.layout.activity_change_tag) {

    private val tagViewModel by viewModels<ChangeTagViewModel>()

    private var _adapter: ChangeTagAdapter? = null
    private val adapter get() = requireNotNull(_adapter) { getString(R.string.adapter_not_initialized_error_msg) }

    private val preferenceAnswers = MutableList(5) { 0 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()
        initPreferenceList()
        initItemDecoration()
        initChangeClickListener()
        initBackClickListener()
        observePreferencePatchState()
    }

    private fun initAdapter() {
        _adapter = ChangeTagAdapter(::preferenceTagClickListener)
        binding.rvChangeTag.adapter = adapter
    }

    private fun initPreferenceList() {
        if (intent != null) {
            val styleA = intent.getIntExtra(STYLE_A, 0)
            val styleB = intent.getIntExtra(STYLE_B, 0)
            val styleC = intent.getIntExtra(STYLE_C, 0)
            val styleD = intent.getIntExtra(STYLE_D, 0)
            val styleE = intent.getIntExtra(STYLE_E, 0)

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
        preferenceAnswers[item.number.toInt() - 1] = checkedIndex
        setButtonValid()
        sendPreferenceInfo()
    }

    private fun setButtonValid() {
        binding.btnChangeStart.isEnabled = true
        binding.btnChangeStart.setTextColor(
            colorOf(R.color.white_000)
        )
    }

    private fun sendPreferenceInfo() {
        tagViewModel.tripId = intent.getLongExtra(TRIP_ID, 0)
        tagViewModel.styleA.value = preferenceAnswers[0]
        tagViewModel.styleB.value = preferenceAnswers[1]
        tagViewModel.styleC.value = preferenceAnswers[2]
        tagViewModel.styleD.value = preferenceAnswers[3]
        tagViewModel.styleE.value = preferenceAnswers[4]
    }

    private fun initItemDecoration() {
        val itemDeco = RVItemFirstLastDecoration(50, 30)
        binding.rvChangeTag.addItemDecoration(itemDeco)
    }

    private fun initChangeClickListener() {
        binding.btnChangeStart.setOnSingleClickListener {
            tagViewModel.patchPreferenceTagToServer()
        }
    }

    private fun initBackClickListener() {
        binding.btnChangeTagBack.setOnSingleClickListener {
            finish()
        }
    }

    private fun observePreferencePatchState() {
        tagViewModel.preferencePatchState.flowWithLifecycle(lifecycle).onEach { result ->
            if (result) {
                toast(getString(R.string.change_tag_success))
                setResult(Activity.RESULT_OK)
                finish()
                return@onEach
            }
            toast(getString(R.string.change_tag_failure))
        }.launchIn(lifecycleScope)
    }

    companion object {
        private const val STYLE_A = "styleA"
        private const val STYLE_B = "styleB"
        private const val STYLE_C = "styleC"
        private const val STYLE_D = "styleD"
        private const val STYLE_E = "styleE"
        private const val TRIP_ID = "tripId"

        @JvmStatic
        fun createIntent(
            context: Context,
            styleA: Int,
            styleB: Int,
            styleC: Int,
            styleD: Int,
            styleE: Int,
            tripId: Long
        ): Intent = Intent(context, ChangeTagActivity::class.java).apply {
            putExtra(STYLE_A, styleA)
            putExtra(STYLE_B, styleB)
            putExtra(STYLE_C, styleC)
            putExtra(STYLE_D, styleD)
            putExtra(STYLE_E, styleE)
            putExtra(TRIP_ID, tripId)
        }
    }

}