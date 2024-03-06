package com.going.presentation.profile.trip.tag

import androidx.lifecycle.ViewModel
import com.going.domain.entity.PreferenceData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TripProfileTagViewModel @Inject constructor(

) : ViewModel() {

    val preferenceTagList = listOf<PreferenceData>(
        PreferenceData(
            number = "01",
            question = "계획은 어느 정도로 세울까요?",
            leftPrefer = "철저하게",
            rightPrefer = "즉흥으로",
        ),
        PreferenceData(
            number = "02",
            question = "장소 선택의 기준은 무엇인가요?",
            leftPrefer = "관광지",
            rightPrefer = "로컬장소",
        ),
        PreferenceData(
            number = "03",
            question = "어느 식당을 갈까요?",
            leftPrefer = "유명 맛집",
            rightPrefer = "가까운 곳",
        ),
        PreferenceData(
            number = "04",
            question = "기억하고 싶은 순간에!",
            leftPrefer = "사진필수",
            rightPrefer = "눈에 담기",
        ),
        PreferenceData(
            number = "05",
            question = "하루 일정을 어떻게 채우나요?",
            leftPrefer = "알차게",
            rightPrefer = "여유롭게",
        ),
    )

}