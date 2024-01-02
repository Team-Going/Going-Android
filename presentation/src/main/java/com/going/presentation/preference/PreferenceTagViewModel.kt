package com.going.presentation.preference

import androidx.lifecycle.ViewModel
import com.going.domain.entity.PreferenceData


class PreferenceTagViewModel : ViewModel() {

    val preferenceTagList = listOf<PreferenceData>(
        PreferenceData(
            number = 1,
            leftPrefer = "휴식",
            rightPrefer = "관광"
        ),
        PreferenceData(
            number = 2,
            leftPrefer = "관광지",
            rightPrefer = "로컬 플레이스"
        ),
        PreferenceData(
            number = 3,
            leftPrefer = "철처한 계획",
            rightPrefer = "무계획"
        ),
        PreferenceData(
            number = 4,
            leftPrefer = "찾아온 맛집",
            rightPrefer = "끌리는 곳"
        ),
        PreferenceData(
            number = 5,
            leftPrefer = "느긋하게",
            rightPrefer = "효율적으로"
        ),
    )

}