package com.going.presentation.splash

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import com.going.presentation.BuildConfig
import com.going.presentation.R
import com.going.presentation.auth.SignInActivity
import com.going.presentation.databinding.ActivitySplashBinding
import com.going.ui.base.BaseActivity
import com.going.ui.extension.toast
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability
import timber.log.Timber

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val appUpdateManager by lazy { AppUpdateManagerFactory.create(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppUpdate()
    }

    private fun initAppUpdate() {
        if (NetworkManager.checkNetworkState(this)) {
            if (BuildConfig.DEBUG) {
                initSplash()
            } else {
                val appUpdateInfoTask = appUpdateManager.appUpdateInfo
                appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                        appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)
                        requestUpdate(appUpdateInfo)
                    } else {
                        initSplash()
                    }
                }.addOnFailureListener {
                    initSplash()
                }
            }
        } else {
            AlertDialog.Builder(this)
                .setTitle(R.string.notice)
                .setMessage(R.string.internet_connect_error)
                .setCancelable(false)
                .setPositiveButton(
                    R.string.okay,
                ) { _, _ ->
                    finishAffinity()
                }
                .create()
                .show()
        }
    }

    private fun initSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToSignInScreen()
            if (false) { // 자동 로그인 판정으로 변경 예정
                navigateToMainScreen()
            } else {
                navigateToSignInScreen()
            }
        }, 3000)
    }

    private fun navigateToMainScreen() {
        // Main이 나오면 구현 예정
        finish()
    }

    private fun navigateToSignInScreen() {
        Intent(this, SignInActivity::class.java).apply {
            startActivity(this)
        }
        finish()
    }

    private fun requestUpdate(appUpdateInfo: AppUpdateInfo) {
        runCatching {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                activityResultLauncher,
                AppUpdateOptions.newBuilder(IMMEDIATE)
                    .setAllowAssetPackDeletion(true)
                    .build(),
            )
        }.onFailure {
            Timber.e(it)
        }
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            if (it.resultCode != RESULT_OK) {
                toast(getString(R.string.splash_update_error))
                finishAffinity()
            }
        }

    override fun onResume() {
        super.onResume()

        if (!BuildConfig.DEBUG) {
            appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    runCatching {
                        appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            activityResultLauncher,
                            AppUpdateOptions.newBuilder(IMMEDIATE)
                                .build(),
                        )
                    }.onFailure { errorMessage ->
                        Timber.e(errorMessage)
                    }
                }
            }
        }
    }
}