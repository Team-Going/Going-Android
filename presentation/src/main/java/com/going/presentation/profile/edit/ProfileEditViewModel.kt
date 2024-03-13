package com.going.presentation.profile.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.request.UserProfileRequestModel
import com.going.domain.repository.ProfileRepository
import com.going.presentation.onboarding.signup.SignUpViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
) : ViewModel() {
    private val _isValueChanged = MutableStateFlow(false)
    val isValueChanged: StateFlow<Boolean> = _isValueChanged

    private val _isChangedSuccess = MutableSharedFlow<Boolean>()
    val isChangedSuccess: SharedFlow<Boolean>
        get() = _isChangedSuccess

    private var isNameChanged = false
    private var isInfoChanged = false

    private lateinit var nowName: String
    private lateinit var nowInfo: String
    private lateinit var defaultName: String
    private lateinit var defaultInfo: String

    fun setDefaultValues(name: String, info: String) {
        nowName = name
        nowInfo = info

        defaultName = name
        defaultInfo = info
    }

    fun checkIsNameChanged(name: String) {
        nowName = name
        isNameChanged = name != defaultName

        checkIsValueChanged()
    }

    fun checkIsInfoChanged(info: String) {
        nowInfo = info
        isInfoChanged = info != defaultInfo

        checkIsValueChanged()
    }

    private fun checkIsValueChanged() {
        _isValueChanged.value =
            nowName.isNotBlank() && nowName.length <= getMaxNameLen() && nowInfo.isNotBlank() && nowInfo.length <= getMaxInfoLen() && (isInfoChanged || isNameChanged)
    }

    fun patchUserInfo() {
        viewModelScope.launch {
            profileRepository.patchUserProfile(UserProfileRequestModel(nowName, nowInfo))
                .onSuccess {
                    _isChangedSuccess.emit(true)
                }.onFailure {
                    _isChangedSuccess.emit(false)
                }
        }
    }

    fun getMaxNameLen() = SignUpViewModel.MAX_NAME_LEN

    fun getMaxInfoLen() = SignUpViewModel.MAX_INFO_LEN
}
