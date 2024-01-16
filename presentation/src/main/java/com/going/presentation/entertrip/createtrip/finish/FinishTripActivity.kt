package com.going.presentation.entertrip.createtrip.finish

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.going.presentation.R
import com.going.presentation.dashboard.DashBoardActivity
import com.going.presentation.databinding.ActivityFinishTripBinding
import com.going.presentation.entertrip.invitetrip.finish.InviteFinishActivity.Companion.DATE_FORMAT
import com.going.presentation.entertrip.invitetrip.finish.InviteFinishActivity.Companion.D_DAY_FORMAT
import com.going.presentation.entertrip.invitetrip.finish.InviteFinishActivity.Companion.TRIP_FORMAT
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.DAY
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.END
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.INVITE_CODE
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.START
import com.going.presentation.entertrip.invitetrip.invitecode.EnterTripActivity.Companion.TITLE
import com.going.presentation.util.initOnBackPressedListener
import com.going.ui.base.BaseActivity
import com.going.ui.extension.setOnSingleClickListener
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import timber.log.Timber


class FinishTripActivity :
    BaseActivity<ActivityFinishTripBinding>(R.layout.activity_finish_trip) {

    private var inviteCode: String = ""
    private var title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getTripInfo()
        initCopyCodetvClickListener()
        initSendCodeBtnClickListener()
        initEnterTripBtnClickListener()
        initOnBackPressedListener()
    }

    private fun initCopyCodetvClickListener() {
        binding.tvFinishTripTermsText.setOnSingleClickListener {
            val clipboardManager =
                this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("INVITE_CODE_LABEL", inviteCode)
            clipboardManager.setPrimaryClip(clipData)
        }
    }

    private fun initSendCodeBtnClickListener() {
        binding.btnFinishTripSendCode.setOnSingleClickListener {
            startKakaoInvite(this)
        }
    }

    private fun startKakaoInvite(context: Context) {
        val test = HashMap<String, String>()
        test.put(KEY, inviteCode)
        test.put(NAME, title)

        if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
            ShareClient.instance.shareCustom(
                context,
                TEMPLATE_ID.toLong(),
                hashMapOf(
                    KEY to inviteCode,
                    NAME to title
                )
            ) { sharingResult, error ->
                if (error != null) {
                    Timber.tag(TAG_SHARE).e(error, getString(R.string.invite_error_kakao))
                } else if (sharingResult != null) {
                    startActivity(sharingResult.intent)
                }
            }
        } else {
            val sharerUrl =
                WebSharerClient.instance.makeCustomUrl(
                    TEMPLATE_ID.toLong(),
                    hashMapOf(
                        KEY to inviteCode,
                        NAME to title
                    )
                )
            try {
                KakaoCustomTabsClient.openWithDefault(context, sharerUrl)
                return
            } catch (error: UnsupportedOperationException) {
                Timber.tag(TAG_SHARE).e(error, getString(R.string.invite_error_browser))
            }
            try {
                KakaoCustomTabsClient.open(context, sharerUrl)
                return
            } catch (error: ActivityNotFoundException) {
                Timber.tag(TAG_SHARE).e(error, getString(R.string.invite_error_browser))
            }
        }
    }

    private fun initEnterTripBtnClickListener() {
        binding.btnFinishTripEnterTrip.setOnSingleClickListener {
            Intent(this, DashBoardActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(this)
            }
            finish()
        }
    }

    private fun getTripInfo() {
        if (intent != null) {
            title = intent.getStringExtra(TITLE) ?: ""
            val start = intent.getStringExtra(START)
            val end = intent.getStringExtra(END)
            inviteCode = intent.getStringExtra(INVITE_CODE) ?: ""
            val day = intent.getIntExtra(DAY, 0)

            binding.tvFinishTripName.text = title
            binding.tvFinishTripDay.text = String.format(DATE_FORMAT, start, end)
            binding.tvInviteCode.text = inviteCode

            if (day > 0) {
                binding.tvFinishTripDayLeft.text = String.format(D_DAY_FORMAT, day)
            } else {
                binding.tvFinishTripDayLeft.text = TRIP_FORMAT
            }
        }
    }

    companion object {
        const val TAG_SHARE = "recommendInvite"
        const val TEMPLATE_ID = 102829
        const val KEY = "KEY"
        const val NAME = "NAME"

    }

}

