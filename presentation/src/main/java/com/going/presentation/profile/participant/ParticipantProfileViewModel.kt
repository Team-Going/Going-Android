package com.going.presentation.profile.participant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.request.ParticipantProfileRequestModel
import com.going.domain.entity.response.ParticipantProfileResponseModel
import com.going.domain.repository.ProfileRepository
import com.going.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParticipantProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
) : ViewModel() {
    private val _participantProfileState =
        MutableStateFlow<UiState<ParticipantProfileResponseModel>>(UiState.Empty)
    val participantProfileState: StateFlow<UiState<ParticipantProfileResponseModel>> =
        _participantProfileState

    var number: Int = 0

    fun getUserInfoState(participantId: Long) {
        viewModelScope.launch {
            _participantProfileState.value = UiState.Loading
            profileRepository.getParticipantProfile(ParticipantProfileRequestModel(participantId))
                .onSuccess {
                    number = it.result
                    _participantProfileState.value = UiState.Success(it)
                }.onFailure {
                    _participantProfileState.value = UiState.Failure(it.message.toString())
                }
        }
    }
}