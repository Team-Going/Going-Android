package com.going.presentation.tripdashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.going.presentation.R
import com.going.presentation.databinding.ActivityTripDashBoardBinding
import com.going.ui.base.BaseActivity

class TripDashBoardActivity :BaseActivity<ActivityTripDashBoardBinding>(R.layout.activity_trip_dash_board) {

    private val viewModel by viewModels<TripDashBoardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}