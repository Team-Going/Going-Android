package com.going.presentation.preference

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityPreferenceTagBinding
import com.going.ui.base.BaseActivity

class PreferenceTagActivity :
    BaseActivity<ActivityPreferenceTagBinding>(R.layout.activity_preference_tag) {

    private val viewModel by viewModels<PreferenceTagViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}