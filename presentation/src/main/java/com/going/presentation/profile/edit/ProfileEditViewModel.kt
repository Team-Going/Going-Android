package com.going.presentation.profile.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.request.UserProfileRequestModel
import com.going.domain.repository.ProfileRepository
import com.going.presentation.onboarding.signup.SignUpViewModel
import com.going.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
) : ViewModel() {

    private val _userInfoState = MutableStateFlow<UiState<UserProfileRequestModel>>(UiState.Empty)
    val userInfoState: StateFlow<UiState<UserProfileRequestModel>> = _userInfoState

    val profileId = MutableStateFlow(0)

    fun getMaxNameLen() = SignUpViewModel.MAX_NAME_LEN

    fun getMaxInfoLen() = SignUpViewModel.MAX_INFO_LEN
}
