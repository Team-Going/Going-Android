package com.going.presentation.preferencetag.invitefinish

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.PreferenceData
import com.going.domain.entity.request.StartInviteTripRequestModel
import com.going.domain.entity.response.StartInviteTripModel
import com.going.domain.repository.StartInviteTripRepository
import com.going.ui.extension.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinishPreferenceViewModel @Inject constructor(
    private val startInviteTripRepository: StartInviteTripRepository
) : ViewModel() {

    private val _finishInviteState =
        MutableStateFlow<UiState<StartInviteTripModel>>(UiState.Empty)
    val finishInviteState: StateFlow<UiState<StartInviteTripModel>> = _finishInviteState

    var tripId = MutableLiveData<Long>()

    val styleA = MutableLiveData(0)
    val styleB = MutableLiveData(0)
    val styleC = MutableLiveData(0)
    val styleD = MutableLiveData(0)
    val styleE = MutableLiveData(0)


    fun checkStyleFromServer(tripId: Long) {
        _finishInviteState.value = UiState.Loading
        viewModelScope.launch {
            startInviteTripRepository.postStartInviteTrip(
                tripId,
                StartInviteTripRequestModel(
                    styleA.value ?: 0,
                    styleB.value ?: 0,
                    styleC.value ?: 0,
                    styleD.value ?: 0,
                    styleE.value ?: 0
                )
            )
                .onSuccess {
                    _finishInviteState.value = UiState.Success(it)
                }.onFailure {
                    _finishInviteState.value = UiState.Failure(it.message.orEmpty())
                }
        }
    }


    val preferenceTagList = listOf<PreferenceData>(
        PreferenceData(
            number = "01",
            question = "계획은 어느정도로 세울까요?",
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
            question = "어느 식당을 갈까요?",
            leftPrefer = "유명 맛집",
            rightPrefer = "가까운 곳"
        ),
        PreferenceData(
            number = "04",
            question = "기억하고 싶은 순간에!",
            leftPrefer = "사진 필수",
            rightPrefer = "눈에 담기"
        ),
        PreferenceData(
            number = "05",
            question = "하루 일정을 어떻게 채우나요?",
            leftPrefer = "알차게",
            rightPrefer = "여유롭게"
        ),
    )

}

