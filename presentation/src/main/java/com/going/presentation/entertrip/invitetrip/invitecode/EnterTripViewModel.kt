package com.going.presentation.entertrip.invitetrip.invitecode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.going.domain.entity.CodeState
import com.going.domain.entity.request.EnterTripRequestModel
import com.going.domain.entity.response.EnterTripModel
import com.going.domain.repository.EnterTripRepository
import com.going.ui.extension.UiState
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
    var isCheckEnterAvailable = MutableLiveData(false)

    fun checkCodeAvailable() {
        codeLength.value = getCodeLength(inviteCode.value)
        isCodeAvailable.value = when {
            codeLength.value == 0 -> CodeState.Empty
            inviteCode.value.isNullOrBlank() -> CodeState.Blank
            !isCodeValid(inviteCode.value) -> CodeState.Invalid
            else -> CodeState.Success.also { checkEnterAvailable() }
        }
    }

    private fun getCodeLength(value: String?) = value?.length ?: 0

    private fun isCodeValid(code: String?) = code?.matches(ENG_NUM_REGEX.toRegex()) ?: false

    fun checkEnterAvailable() {
        isCheckEnterAvailable.value = isCodeAvailable.value == CodeState.Success
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
                    val jsonObject = JSONObject(errorResponse)
                    val errorCode = jsonObject.getString("code")
                    val errorMessage = jsonObject.getString("message")

                    if (errorCode == NO_TRIP_CODE_ERROR) {
                        _tripState.value = UiState.Failure(errorMessage)
                    }
                } else {
                    _tripState.value = UiState.Failure(throwable.message.orEmpty())
                }
            }
        }
    }

    companion object {
        private const val ENG_NUM_PATTERN = "^[a-z0-9]*$"
        val ENG_NUM_REGEX: Pattern = Pattern.compile(ENG_NUM_PATTERN)
        const val MAX_INVITE_LEN = 6
        const val NO_TRIP_CODE_ERROR = "e4043"
    }
}
