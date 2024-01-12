package com.going.presentation.tendency.ttest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.TendencyTestMock
import com.going.domain.entity.request.TendencyRequestModel
import com.going.domain.repository.TendencyRepository
import com.going.ui.extension.EnumUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TendencyTestViewModel @Inject constructor(
    private val tendencyRepository: TendencyRepository,
) : ViewModel() {
    val step = MutableStateFlow(1)
    val isChecked = MutableLiveData(false)
    val isFirstChecked = MutableStateFlow(false)
    val isSecondChecked = MutableStateFlow(false)
    val isThirdChecked = MutableStateFlow(false)
    val isFourthChecked = MutableStateFlow(false)

    private val tendencyResultList: MutableList<Int> = MutableList(9) { 0 }

    private val _isSubmitTendencyState = MutableStateFlow(EnumUiState.EMPTY)
    val isSubmitTendencyState: StateFlow<EnumUiState> = _isSubmitTendencyState

    val mockList: List<TendencyTestMock> = listOf(
        TendencyTestMock(
            "어떤 여행지에 가시겠어요?",
            "사람들이 많이 방문한 대중적인 곳",
            "교통이 발달하고 치안이 좋아 안전한 곳",
            "여기서만 경험할 수 있는 활동이 있는 곳",
            "아무도 가보지 않은, 숨겨진 명소가 있는 곳",
        ),
        TendencyTestMock(
            "여행을 가게 되면 먼저 할 일은?",
            "예약 가능한 비행기 표와 숙소 알아보기",
            "다른 사람이 다녀온 여행 후기 찾아보기",
            "가고 싶은 맛집, 명소 찾아보기",
            "여기에서만 할 수 있는 액티비티 찾아보기",
        ),
        TendencyTestMock(
            "갑자기 특이한 메뉴를 추천받으면?",
            "도전하기보다는 원래 정했던 메뉴를 주문한다",
            "구글맵에 후기를 검색한다",
            "한국에는 없잖아! 바로 주문한다",
            "오히려 좋아. 더 추천해달라고 한다",
        ),
        TendencyTestMock(
            "친구의 지인이 동행하자고 한다면?",
            "새 친구랑 친해질 수 있는 기회! 너무 좋다",
            "잘 맞으면 너무 좋을 것 같은데? 어떤 사람일까?",
            "불편하지만 우선 고민해보겠다고 말한다",
            "\"우리끼리 여행하는 게 좋아\" 라고 거절한다",
        ),
        TendencyTestMock(
            "친구가 하루만 혼자 놀겠다 하면?",
            "하고 싶은 걸 물어보고 일정을 바꾼다",
            "보내주지만 속으로는 불만이 있을까 걱정한다",
            "하고 싶은 게 있나 보다' 생각하며 보내준다",
            "그럼 오늘은 각자 시간을 보내자고 제안한다",
        ),
        TendencyTestMock(
            "여행 마지막 날 숙소에서 나는?",
            "친구들과 함께 야식을 먹으며 이야기 나누기",
            "아쉬우니까 같이 파티 타임 가질까?",
            "다음 날의 일정을 위해 빠르게 잠에 들기",
            "각자 핸드폰을 보며 개인 시간 보내기",
        ),
        TendencyTestMock(
            "우연히 예쁜 소품샵을 발견했다면?",
            "들어가면 일정에 차질이 생기는지 확인하기",
            "가볼 만한 곳인지 검색해 보기",
            "운명이라고 생각하며 사진부터 찍는다",
            "일단 들어가서 이것저것 구경한다",
        ),
        TendencyTestMock(
            "여행 계획을 세우는 방법은?",
            "시간별 일정을 세우고 정해진 대로 움직이기",
            "큰 계획만 세우고 상황 따라 일정 조정하기",
            "가고 싶은 곳만 정하고 그때그때 선택하기",
            "상황에 맞춰 무엇을 할지 즉흥으로 고민하기",
        ),
        TendencyTestMock(
            "가려고 한 식당이 문을 닫았다면?",
            "이미 영업시간을 체크해서 그런 일은 없다",
            "다른 날에라도 갈 수 있게 일정을 조정한다",
            "옆에 있는 다른 식당에 들어가 본다",
            "이것도 추억이지 근처 가까운 식당으로 간다",
        ),
    )

    fun plusStepValue() {
        step.value = step.value.plus(1)
    }

    fun setCheckedValue(id: Int) {
        clearAllChecked()
        isChecked.value = true
        tendencyResultList[step.value - 1] = id

        when (id) {
            0 -> isFirstChecked.value = true
            1 -> isSecondChecked.value = true
            2 -> isThirdChecked.value = true
            3 -> isFourthChecked.value = true
        }
    }

    fun clearAllChecked() {
        isChecked.value = false
        isFirstChecked.value = false
        isSecondChecked.value = false
        isThirdChecked.value = false
        isFourthChecked.value = false
    }

    fun submitTendencyTest() {
        viewModelScope.launch {
            _isSubmitTendencyState.value = EnumUiState.LOADING
            tendencyRepository.patchTendencyTest(TendencyRequestModel(tendencyResultList))
                .onSuccess {
                    _isSubmitTendencyState.value = EnumUiState.SUCCESS
                }.onFailure {
                    _isSubmitTendencyState.value = EnumUiState.FAILURE
                }
        }
    }

    companion object {
        const val MAX_STEP = 9
    }
}
