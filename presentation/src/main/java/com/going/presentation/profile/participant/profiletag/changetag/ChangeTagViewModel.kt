package com.going.presentation.profile.participant.profiletag.changetag

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

    private var defaultStyleA: Int? = 0
    private var defaultStyleB: Int? = 0
    private var defaultStyleC: Int? = 0
    private var defaultStyleD: Int? = 0
    private var defaultStyleE: Int? = 0

    private var styleA: Int? = 0
    private var styleB: Int? = 0
    private var styleC: Int? = 0
    private var styleD: Int? = 0
    private var styleE: Int? = 0

    private var isStyleAChanged: Boolean = false
    private var isStyleBChanged: Boolean = false
    private var isStyleCChanged: Boolean = false
    private var isStyleDChanged: Boolean = false
    private var isStyleEChanged: Boolean = false

    fun setDefaultPreference(styleA: Int, styleB: Int, styleC: Int, styleD: Int, styleE: Int) {
        defaultStyleA = styleA
        this.styleA = styleA

        defaultStyleB = styleB
        this.styleB = styleB

        defaultStyleC = styleC
        this.styleC = styleC

        defaultStyleD = styleD
        this.styleD = styleD

        defaultStyleE = styleE
        this.styleE = styleE
    }

    fun checkIsPreferenceChange(number: Int, index: Int) {
        when (number) {
            1 -> {
                styleA = index
                isStyleAChanged = index != defaultStyleA
            }

            2 -> {
                styleB = index
                isStyleBChanged = index != defaultStyleB
            }

            3 -> {
                styleC = index
                isStyleCChanged = index != defaultStyleC
            }

            4 -> {
                styleD = index
                isStyleDChanged = index != defaultStyleD
            }

            5 -> {
                styleE = index
                isStyleEChanged = index != defaultStyleE
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
                    styleA ?: 0,
                    styleB ?: 0,
                    styleC ?: 0,
                    styleD ?: 0,
                    styleE ?: 0
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