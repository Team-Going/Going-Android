package com.going.presentation.entertrip.invitetrip.invitecode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.CodeState
import com.going.domain.entity.request.EnterTripRequestModel
import com.going.domain.entity.response.EnterTripModel
import com.going.domain.repository.EnterTripRepository
import com.going.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class EnterTripViewModel @Inject constructor(
    private val enterTripRepository: EnterTripRepository
) : ViewModel() {

    private val _tripState = MutableStateFlow<UiState<EnterTripModel>>(UiState.Empty)
    val tripState: StateFlow<UiState<EnterTripModel>> = _tripState

    val inviteCode = MutableLiveData<String>()
    var codeLength = MutableLiveData(0)

    val isCodeAvailable = MutableLiveData(CodeState.Empty)
    val isInviteCodeAvailable = MutableLiveData(false)

    fun checkCodeAvailable() {
        codeLength.value = getCodeLength(inviteCode.value)
        isCodeAvailable.value = when {
            codeLength.value == 0 -> CodeState.Empty
            inviteCode.value.isNullOrBlank() -> CodeState.Blank
            !isCodeValid(inviteCode.value) -> CodeState.Invalid
            else -> CodeState.Success
        }

        val isLengthAvailable = codeLength.value in 1..MAX_INVITE_LEN

        isInviteCodeAvailable.value =
            (isCodeAvailable.value == CodeState.Success) && isLengthAvailable

        checkEnterAvailable()
    }

    private fun getCodeLength(value: String?) = value?.length ?: 0

    private fun isCodeValid(code: String?) =
        code?.matches(ENG_NUM_REGEX.toRegex()) == true && code.length == 6

    fun checkEnterAvailable() {
        isInviteCodeAvailable.value = isCodeAvailable.value == CodeState.Success
    }

    fun checkInviteCodeFromServer() {
        _tripState.value = UiState.Loading
        viewModelScope.launch {
            enterTripRepository.postEnterTrip(
                EnterTripRequestModel(inviteCode.value ?: "")
            ).onSuccess {
                _tripState.value = UiState.Success(it)
            }.onFailure { throwable ->
                if (throwable is HttpException) {
                    val errorResponse = throwable.response()?.errorBody()?.string()
                    val jsonObject = JSONObject(errorResponse.orEmpty())
                    val errorCode = jsonObject.getString("code")
                    _tripState.value = UiState.Failure(errorCode)
                }
            }
        }
    }

    companion object {
        private const val ENG_NUM_PATTERN = "^[a-z0-9]*$"
        val ENG_NUM_REGEX: Pattern = Pattern.compile(ENG_NUM_PATTERN)
        const val MAX_INVITE_LEN = 6
        const val ERROR_NO_EXIST = "e4043"
        const val ERROR_OVER_SIX = "e4006"
        const val ERROR_ALREADY_EXIST = "e4092"
    }
}
