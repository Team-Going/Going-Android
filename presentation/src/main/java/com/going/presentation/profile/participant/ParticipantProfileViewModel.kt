package com.going.presentation.profile.participant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.ProfilePreferenceData
import com.going.domain.entity.request.ParticipantProfileRequestModel
import com.going.domain.entity.response.ParticipantProfileResponseModel
import com.going.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParticipantProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
) : ViewModel() {
    private val _participantProfile =
        MutableSharedFlow<ParticipantProfileResponseModel?>()
    val participantProfile: SharedFlow<ParticipantProfileResponseModel?> = _participantProfile

    lateinit var profileTmp: ParticipantProfileResponseModel

    var number: Int = 0
    var tripId: Long = 0

    fun getUserInfoState(participantId: Long) {
        viewModelScope.launch {
            profileRepository.getParticipantProfile(ParticipantProfileRequestModel(participantId))
                .onSuccess {
                    number = it.result
                    profileTmp = it
                    _participantProfile.emit(it)
                }.onFailure {
                    _participantProfile.emit(null)
                }
        }
    }

    fun setPreferenceData(styleA: Int, styleB: Int, styleC: Int, styleD: Int, styleE: Int) = listOf(
        ProfilePreferenceData("01", "계획은 어느 정도로 세울까요?", "철저하게", "즉흥으로", styleA),
        ProfilePreferenceData("02", "장소 선택의 기준은 무엇인가요?", "관광지", "로컬장소", styleB),
        ProfilePreferenceData("03", "어느 식당을 갈까요?", "유명 맛집", "가까운 곳", styleC),
        ProfilePreferenceData("04", "기억하고 싶은 순간에!", "사진필수", "눈에 담기", styleD),
        ProfilePreferenceData("05", "하루 일정을 어떻게 채우나요?", "알차게", "여유롭게", styleE)
    )
}