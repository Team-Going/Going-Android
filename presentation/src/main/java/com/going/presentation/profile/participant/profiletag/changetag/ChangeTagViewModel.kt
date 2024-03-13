package com.going.presentation.profile.participant.profiletag.changetag

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.ProfilePreferenceData
import com.going.domain.entity.request.PreferenceChangeRequestModel
import com.going.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeTagViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _preferencePatchState = MutableSharedFlow<Boolean>()
    val preferencePatchState: SharedFlow<Boolean> = _preferencePatchState

    private val _isButtonValid = MutableStateFlow(false)
    val isButtonValid: StateFlow<Boolean> = _isButtonValid

    var tripId: Long = 0

    private val defaultStyleA = MutableLiveData(0)
    private val defaultStyleB = MutableLiveData(0)
    private val defaultStyleC = MutableLiveData(0)
    private val defaultStyleD = MutableLiveData(0)
    private val defaultStyleE = MutableLiveData(0)

    private val styleA = MutableLiveData(0)
    private val styleB = MutableLiveData(0)
    private val styleC = MutableLiveData(0)
    private val styleD = MutableLiveData(0)
    private val styleE = MutableLiveData(0)

    private var isStyleAChanged: Boolean = false
    private var isStyleBChanged: Boolean = false
    private var isStyleCChanged: Boolean = false
    private var isStyleDChanged: Boolean = false
    private var isStyleEChanged: Boolean = false

    fun setDefaultPreference(styleA: Int, styleB: Int, styleC: Int, styleD: Int, styleE: Int) {
        defaultStyleA.value = styleA
        this.styleA.value = styleA

        defaultStyleB.value = styleB
        this.styleB.value = styleB

        defaultStyleC.value = styleC
        this.styleC.value = styleC

        defaultStyleD.value = styleD
        this.styleD.value = styleD

        defaultStyleE.value = styleE
        this.styleE.value = styleE
    }

    fun checkIsPreferenceChange(number: Int, index: Int) {
        when (number) {
            1 -> {
                styleA.value = index
                isStyleAChanged = index != defaultStyleA.value
            }

            2 -> {
                styleB.value = index
                isStyleBChanged = index != defaultStyleB.value
            }

            3 -> {
                styleC.value = index
                isStyleCChanged = index != defaultStyleC.value
            }

            4 -> {
                styleD.value = index
                isStyleDChanged = index != defaultStyleD.value
            }

            5 -> {
                styleE.value = index
                isStyleEChanged = index != defaultStyleE.value
            }
        }
        checkIsButtonValid()
    }

    private fun checkIsButtonValid() {
        _isButtonValid.value =
            isStyleAChanged || isStyleBChanged || isStyleCChanged || isStyleDChanged || isStyleEChanged
    }

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