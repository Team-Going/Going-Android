package com.going.presentation.preferencetag

import androidx.lifecycle.ViewModel
import com.going.domain.entity.PreferenceData

class PreferenceTagViewModel : ViewModel() {

    val preferenceTagList = listOf<PreferenceData>(
        PreferenceData(
            number = "01",
            question = "계획은 얼만큼 짤까요?",
            leftPrefer = "철저하게",
            rightPrefer = "즉흥으로"
        ),
        PreferenceData(
            number = "02",
            question = "장소선택의 기준은 무엇인가요?",
            leftPrefer = "관광지",
            rightPrefer = "로컬장소"
        ),
        PreferenceData(
            number = "03",
            question = "음식을 고를 때 무엇을 더 중요시 하나요?",
            leftPrefer = "철저한 계획",
            rightPrefer = "무계획"
        ),
        PreferenceData(
            number = "04",
            question = "멋진 풍경이 보이면?",
            leftPrefer = "사진 필수",
            rightPrefer = "눈에 담기"
        ),
        PreferenceData(
            number = "05",
            question = "스케줄 구성은 어떻게 할까요?",
            leftPrefer = "알차게",
            rightPrefer = "여유롭게"
        ),
    )

}