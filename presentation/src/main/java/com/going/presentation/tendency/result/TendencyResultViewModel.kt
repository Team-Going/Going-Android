package com.going.presentation.tendency.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.request.UserProfileRequestModel
import com.going.domain.repository.ProfileRepository
import com.going.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TendencyResultViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
) : ViewModel() {

    private val _userInfoState = MutableStateFlow<UiState<UserProfileRequestModel>>(UiState.Empty)
    val userInfoState: StateFlow<UiState<UserProfileRequestModel>> = _userInfoState

    val tendencyId = MutableStateFlow(0)

    fun getUserInfoState() {
        viewModelScope.launch {
            _userInfoState.value = UiState.Loading
            profileRepository.getUserProfile().onSuccess {
                tendencyId.value = it.result
                _userInfoState.value = UiState.Success(it)
            }.onFailure {
                _userInfoState.value = UiState.Failure(it.message.toString())
            }
        }
    }
}
