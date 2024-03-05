package com.going.presentation.todo.editinfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.going.presentation.entertrip.createtrip.preference.ParcelableTripData

class EditTripViewModel: ViewModel() {
    val name = MutableLiveData<String>()
    val nameLength = MutableLiveData(0)

    val startYear = MutableLiveData<Int>()
    val startMonth = MutableLiveData<Int>()
    val startDay = MutableLiveData<Int>()

    val endYear = MutableLiveData<Int>()
    val endMonth = MutableLiveData<Int>()
    val endDay = MutableLiveData<Int>()

    var tripIntentData: ParcelableTripData? = null


    fun saveIntentData() {
        tripIntentData = ParcelableTripData( //paecelable 안써도 됨
            name = name.value.orEmpty(),
            startYear = startYear.value ?: 0,
            startMonth = startMonth.value ?: 0,
            startDay = startDay.value ?: 0,
            endYear = endYear.value ?: 0,
            endMonth = endMonth.value ?: 0,
            endDay = endDay.value ?: 0
        )
    }

    companion object {
        const val MAX_TRIP_LEN = 15
    }

}
