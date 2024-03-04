package com.going.presentation.profile.edit

import androidx.lifecycle.ViewModel
import com.going.presentation.onboarding.signup.SignUpViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileEditViewModel : ViewModel() {
    private val _isValueChanged = MutableStateFlow(false)
    val isValueChanged: StateFlow<Boolean> = _isValueChanged

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
            nowName.length <= getMaxNameLen() && nowInfo.length <= getMaxInfoLen() && (isInfoChanged || isNameChanged)
    }

    fun getMaxNameLen() = SignUpViewModel.MAX_NAME_LEN

    fun getMaxInfoLen() = SignUpViewModel.MAX_INFO_LEN
}
