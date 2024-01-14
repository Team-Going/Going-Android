package com.going.presentation.preferencetag

import androidx.lifecycle.ViewModel
import com.going.domain.entity.PreferenceData

class PreferenceTagViewModel : ViewModel() {

    // 추후 삭제할 예정
    val preferenceTagList = listOf<PreferenceData>(
        PreferenceData(
            number = "01",
            question = "계획은 어느정도로 세울까요?",
            leftPrefer = "철저하게",
            rightPrefer = "즉흥으로",
        ),
        PreferenceData(
            number = "02",
            question = "어떤 곳을 가고 싶나요?",
            leftPrefer = "명소 외주",
            rightPrefer = "로컬 장소",
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
            question = "하루 일정을 어떻게 구성해요?",
            leftPrefer = "알차게",
            rightPrefer = "여유롭게",
        ),
    )
}
