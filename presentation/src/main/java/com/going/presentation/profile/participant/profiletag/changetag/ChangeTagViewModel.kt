package com.going.presentation.profile.participant.profiletag.changetag

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.ProfilePreferenceData
import com.going.domain.entity.request.PreferenceChangeRequestModel
import com.going.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeTagViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _preferencePatchState = MutableSharedFlow<Boolean>()
    val preferencePatchState: SharedFlow<Boolean> = _preferencePatchState

    var tripId: Long = 0

    val styleA = MutableLiveData(0)
    val styleB = MutableLiveData(0)
    val styleC = MutableLiveData(0)
    val styleD = MutableLiveData(0)
    val styleE = MutableLiveData(0)

    fun patchPreferenceTagToServer() {
        viewModelScope.launch {
            profileRepository.patchPreferenceTag(
                tripId,
                PreferenceChangeRequestModel(
                    styleA.value ?: 0,
                    styleB.value ?: 0,
                    styleC.value ?: 0,
                    styleD.value ?: 0,
                    styleE.value ?: 0
                )
            )
                .onSuccess {
                    _preferencePatchState.emit(true)
                }
                .onFailure {
                    _preferencePatchState.emit(false)
                }
        }
    }

    fun initPreferenceData(styleA: Int, styleB: Int, styleC: Int, styleD: Int, styleE: Int) =
        listOf(
            ProfilePreferenceData("01", "계획은 어느 정도로 세울까요?", "철저하게", "즉흥으로", styleA),
            ProfilePreferenceData("02", "장소 선택의 기준은 무엇인가요?", "관광지", "로컬장소", styleB),
            ProfilePreferenceData("03", "어느 식당을 갈까요?", "유명 맛집", "가까운 곳", styleC),
            ProfilePreferenceData("04", "기억하고 싶은 순간에!", "사진필수", "눈에 담기", styleD),
            ProfilePreferenceData("05", "하루 일정을 어떻게 채우나요?", "알차게", "여유롭게", styleE)
        )

}