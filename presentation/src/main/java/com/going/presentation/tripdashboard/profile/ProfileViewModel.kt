package com.going.presentation.tripdashboard.profile

import androidx.lifecycle.ViewModel
import com.going.domain.entity.ProfileMock
import com.going.presentation.R

class ProfileViewModel : ViewModel() {
    val mockTendencyResult: List<ProfileMock> = listOf(
        ProfileMock(
            resultImage = R.drawable.img_tendency_result_srp,
            profileTitle = "배려심 넘치는 든든잉",
            profileSubTitle = "꼼꼼하고 세심하게 여행을 준비해요",
            tags = listOf(
                "친구와 함께",
                "꼼꼼함",
                "세심함",
            ),
            profileBoxInfo = listOf(
                ProfileMock.BoxInfo(
                    "이런점이\n좋아요",
                    "같이 여행하는 친구들을 잘 챙기고 배려해요",
                    "친구들이 하고 싶어 하는 것들을 일정에 잘 반영해 줘요",
                    "꼼꼼하고 부지런해서 여행 준비에 진심이에요",
                ),
                ProfileMock.BoxInfo(
                    "이런점은\n주의해줘요",
                    "완벽주의 스타일이라 계획이 틀어지면 예민해져요",
                    "생각하지 못한 상황을 직면하면 당황하기도 해요",
                    "너무 시끄러운 곳보다는 우리끼리 시간을 보낼 수 있는 곳을 더 좋아해요",
                ),
                ProfileMock.BoxInfo(
                    "이런걸\n잘해요",
                    "숙소 및 입장권 미리 예약하기",
                    "여행지 조사하고 일정 정리하기",
                    "친구들 일정 조율하기",
                ),
            ),
        ),
        ProfileMock(
            resultImage = R.drawable.img_tendency_result_sri,
            profileTitle = "뭐든지 다 좋아! 와따잉",
            profileSubTitle = "어떤 여행이든, 일정이든 즐겨요",
            tags = listOf(
                "실용적",
                "긍정적",
                "좋은데?",
            ),
            profileBoxInfo = listOf(
                ProfileMock.BoxInfo(
                    "이런점이\n좋아요",
                    "어떤 일정이든 긍정적으로 반응해요",
                    "남들이 모르는 새로운 액티비티, 일정을 찾아와요",
                    "여행 중 생기는 돌발 상황에서 당황하지 않고 방법을 찾아요",
                ),
                ProfileMock.BoxInfo(
                    "이런점은\n주의해줘요",
                    "우유부단해 귀가 얇고 결정을 잘 내리지 못하는 편이에요",
                    "하고 싶은 게 있어도 잘 말하지 않아 다른 사람들을 답답하게 만들기도 해요",
                    "약간 귀차니즘이 있어 중간중간 맡은 일을 잘 하고 있는지 확인이 필요해요",
                ),
                ProfileMock.BoxInfo(
                    "이런걸\n잘해요",
                    "여행의 분위기 메이커 역할",
                    "현지에서만 할 수 있는 체험 찾아보기",
                    "메뉴만 말하면 척척 나오는 맛집 레이더",
                ),
            ),
        ),
        ProfileMock(
            resultImage = R.drawable.img_tendency_result_sep,
            profileTitle = "계획 절대 지켜 꼼꼼잉",
            profileSubTitle = "여행의 처음부터 끝까지 착실하게 계획해요",
            tags = listOf(
                "계획필수",
                "효율추구",
                "꼼꼼한 조사",
            ),
            profileBoxInfo = listOf(
                ProfileMock.BoxInfo(
                    "이런점이\n좋아요",
                    "역할이 주어졌을 때 완벽하게 해내요",
                    "혹시 모를 상황을 대비해서 플랜 A부터 Z까지 준비해요",
                    "여행지의 역사, 날씨, 최근 이슈 등까지 세세하게 찾아보고 알려줘요",
                ),
                ProfileMock.BoxInfo(
                    "이런점은\n주의해줘요",
                    "시간 약속을 어기는 것을 싫어해요",
                    "여행 일정에 차질이 생기면 스트레스를 받곤 해요",
                    "인내심이 많지만 참다가 터질 수 있어요",
                ),
                ProfileMock.BoxInfo(
                    "이런걸\n잘해요",
                    "여행 관련 꿀팁 정리하기",
                    "시간 단위로 일정 계획하기",
                    "여행에서 사용한 금액 계산하고 정리하기",
                ),
            ),
        ),
        ProfileMock(
            resultImage = R.drawable.img_tendency_result_sei,
            profileTitle = "하고싶은건 하는 고잉",
            profileSubTitle = "하고 싶은 것만 하게 해주면, 나머지는 다 괜찮아요",
            tags = listOf(
                "취향존중",
                "여유롭게",
                "잠깐휴식",
            ),
            profileBoxInfo = listOf(
                ProfileMock.BoxInfo(
                    "이런점이\n좋아요",
                    "하고 싶은 것만 할 수 있다면 다른 건 뭐든 오케이",
                    "예기치 못한 상황에서도 침착하게 대안을 찾아요",
                    "계획은 없어도 후기는 꼼꼼하게 확인해서 실패하는 일이 없어요",
                ),
                ProfileMock.BoxInfo(
                    "이런점은\n주의해줘요",
                    "가끔 혼자만의 시간이 필요해요",
                    "꼼꼼한 일정과 계획은 오히려 피곤하게 느껴져요",
                    "만사가 귀찮아 보여도 막상 여행에서는 즐겁게 있으니 걱정 마세요",
                ),
                ProfileMock.BoxInfo(
                    "이런걸\n잘해요",
                    "여행지에서 길 찾기",
                    "숙소 검색 및 예약하기",
                    "현지에서 갈 만한 카페, 식당 리스트업하기",
                ),
            ),
        ),
        ProfileMock(
            resultImage = R.drawable.img_tendency_result_arp,
            profileTitle = "다정한 여행 반장 모여잉",
            profileSubTitle = "즐거운 여행을 위해 모두를 챙겨요",
            tags = listOf(
                "리더십",
                "친절함",
                "알뜰살뜰",
            ),
            profileBoxInfo = listOf(
                ProfileMock.BoxInfo(
                    "이런점이\n좋아요",
                    "준비물, 예약 티켓을 꼼꼼하게 잘 챙겨요",
                    "같이 여행 가는 친구들의 성격, 취향을 세심하게 기억해 줘요",
                    "해야 하는 일이 있다면 솔선수범해서 하는 편이에요",
                ),
                ProfileMock.BoxInfo(
                    "이런점은\n주의해줘요",
                    "친구들이 다투면 엄청난 스트레스를 받아요",
                    "불만을 잘 드러내지 않고 마음에 쌓아두곤 해요",
                    "모두를 잘 챙겨야 한다는 약간의 부담감을 항상 가지고 있어요",
                ),
                ProfileMock.BoxInfo(
                    "이런걸\n잘해요",
                    "여행 전 친구들이 잊은 준비물은 없는지 체크하기",
                    "여행 일정 계획하기",
                    "공금 관리 및 정산하기",
                ),
            ),
        ),
        ProfileMock(
            resultImage = R.drawable.img_tendency_result_ari,
            profileTitle = "깨발랄 치어리더 아쟈잉",
            profileSubTitle = "친구들과 함께 하는 것 자체가 행복해요",
            tags = listOf(
                "낙천적",
                "낭만추구",
                "남는건 사",
            ),
            profileBoxInfo = listOf(
                ProfileMock.BoxInfo(
                    "이런점이\n좋아요",
                    "사진, 동영상 등으로 여행을 많이 기록해요",
                    "친구랑 함께 한다면 무엇이든 좋아요",
                    "같이 가면 무조건 꿀잼 보장!",
                ),
                ProfileMock.BoxInfo(
                    "이런점은\n주의해줘요",
                    "일정 없이 자유롭게 보낼 수 있는 시간도 존중해 주세요",
                    "평소 덜렁거려 잘 다쳐요",
                    "너무 꽉꽉 채운 계획은 부담스러워요",
                ),
                ProfileMock.BoxInfo(
                    "이런걸\n잘해요",
                    "이번 여행 분위기 메이커 역할",
                    "친구들 인생샷 남겨주기",
                    "오늘의 플레이리스트 만들기",
                ),
            ),
        ),
        ProfileMock(
            resultImage = R.drawable.img_tendency_result_aep,
            profileTitle = "나만 믿어 따라와 척척잉",
            profileSubTitle = "모든 것을 계획하고 진행하는 프로총대러에요",
            tags = listOf(
                "내가할게",
                "야무지게",
                "주도적",
            ),
            profileBoxInfo = listOf(
                ProfileMock.BoxInfo(
                    "이런점이\n좋아요",
                    "꼼꼼한 일정 계획으로 알찬 여행을 다녀올 수 있어요",
                    "효율을 추구해 나와 친구가 원하는 곳을 모두 갈 수 있게 일정을 계획해요",
                    "더 알찬 여행을 위해 무한 서칭! 새로운 체험, 경험을 찾아와요",
                ),
                ProfileMock.BoxInfo(
                    "이런점은\n주의해줘요",
                    "길에서 어리둥절하게 서 있는 시간을 싫어해요",
                    "보여주는 일정과 계획에 대해 적극적으로 반응해 주세요",
                    "하고 싶은 일을 할 수 있게 개인 시간도 존중해 주세요",
                ),
                ProfileMock.BoxInfo(
                    "이런걸\n잘해요",
                    "필요한 준비물 리스트업하기",
                    "여행용 톡방 만들고 일정 정하기",
                    "효율적인 이동 동선 짜기",
                ),
            ),
        ),
        ProfileMock(
            resultImage = R.drawable.img_tendency_result_aei,
            profileTitle = "모험을 즐기는 별별잉",
            profileSubTitle = "여행의 진정한 재미는 새로운 도전이에요",
            tags = listOf(
                "도전추구",
                "호기심",
                "적응력",
            ),
            profileBoxInfo = listOf(
                ProfileMock.BoxInfo(
                    "이런점이\n좋아요",
                    "호기심과 도전 정신이 강해 친구들을 새로운 경험으로 이끌어요",
                    "함께하는 친구들에게 피해를 주지 않기 위해 노력해요",
                    "구체적인 일정은 없지만 찾아 놓은 장소, 후보들이 많아요 ",
                ),
                ProfileMock.BoxInfo(
                    "이런점은\n주의해줘요",
                    "하고 싶은 일과 하고 싶지 않은 일이 명확해요",
                    "남들이 다 하는 건 재미없게 느껴요",
                    "말을 하지는 않지만 속으로 많은 것들을 생각 중이에요",
                ),
                ProfileMock.BoxInfo(
                    "이런걸\n잘해요",
                    "빠른 상황 판단 및 해결 방안 제시하기",
                    "사람들이 모르는 숨은 맛집과 관광지 찾기",
                    "현지인에게 질문, 도움 요청하기",
                ),
            ),
        ),
    )

}

