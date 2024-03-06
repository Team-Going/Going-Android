package com.going.presentation.profile.trip

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
    private val _participantProfileState = MutableStateFlow<UiState<ParticipantProfileResponseModel>>(UiState.Empty)
    val participantProfileState: StateFlow<UiState<ParticipantProfileResponseModel>> = _participantProfileState

    val isOwner: Boolean by lazy { (participantProfileState.value as UiState.Success<ParticipantProfileResponseModel>).data.isOwner }
    val result: Int by lazy { (participantProfileState.value as UiState.Success<ParticipantProfileResponseModel>).data.result }
    val styleA: Int by lazy { (participantProfileState.value as UiState.Success<ParticipantProfileResponseModel>).data.styleA }
    val styleB: Int by lazy { (participantProfileState.value as UiState.Success<ParticipantProfileResponseModel>).data.styleA }
    val styleC: Int by lazy { (participantProfileState.value as UiState.Success<ParticipantProfileResponseModel>).data.styleA }
    val styleD: Int by lazy { (participantProfileState.value as UiState.Success<ParticipantProfileResponseModel>).data.styleA }
    val styleE: Int by lazy { (participantProfileState.value as UiState.Success<ParticipantProfileResponseModel>).data.styleA }


    init {
        getUserInfoState()
    }

    private fun getUserInfoState() {
        viewModelScope.launch {
            _participantProfileState.value = UiState.Loading
            profileRepository.getParticipantProfile(ParticipantProfileRequestModel(418L)).onSuccess {
                _participantProfileState.value = UiState.Success(it)
            }.onFailure {
                _participantProfileState.value = UiState.Failure(it.message.toString())
            }
        }
    }
}