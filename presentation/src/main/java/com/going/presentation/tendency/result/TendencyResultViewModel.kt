package com.going.presentation.tendency.result

import androidx.lifecycle.ViewModel
import com.going.domain.entity.ProfileMock
import com.going.presentation.R

class TendencyResultViewModel : ViewModel() {
    val mockTendencyResult: ProfileMock = ProfileMock(
        resultImage = R.drawable.img_tendency_result_aep,
        profileTitle = "배려심 많은 인간 플래너",
        profileSubTitle = "꼼꼼하고 세심하게 여행을 준비하는 친구",
        tags = listOf(
            "친구중심",
            "꼼꼼함",
            "세심함",
        ),
        profileBoxInfo = listOf(
            ProfileMock.BoxInfo(
                "이런점이\n좋아요",
                "같이 가는 친구들을 잘 챙기고 배려해요",
                "친구들의 의견을 잘 반영해 만족할 수 있는 일정을 계획해요",
                "꼼꼼하고 부지런해 맡은 일에서 실수가 적어요",
            ),
            ProfileMock.BoxInfo(
                "이런점은\n주의해줘요",
                "완벽주의 성향이 강해 계획이 틀어졌을 때 예민해질 수 있어요",
                "싸우지 않고 여행하는 것을 중요시 해 갈등의 조짐이 보이면 많은 스트레스를 받아요",
                "예기치 않은 상황에서 크게 당횡해요",
            ),
            ProfileMock.BoxInfo(
                "이런걸\n잘해요",
                "전반적인 여행 계획 마련",
                "호텔과 비행기표 예약 및 관리",
                "기타 행동이 필요한 것들",
            ),
        ),
    )
}
