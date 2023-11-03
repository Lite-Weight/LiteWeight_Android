package com.konkuk.personal.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konkuk.common.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    val name =
        userRepository.nameFlow
            .map {
                if (it == null) "이름을 입력해주세요" else it
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")
    val age =
        userRepository.ageFlow.map {
            if (it == null) "나이을 입력해주세요" else it.toString()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")
    val gender =
        userRepository.genderFlow.map {
            if (it == null) "성별을 입력해주세요" else if (it) "남자" else "여자"
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    val editableName = MutableStateFlow("")
    val editableAge = MutableStateFlow("")
    val editableGender = MutableStateFlow<Boolean?>(null)

    val canEditName = userRepository.nameFlow.combine(editableName) { savedName, editName ->
        savedName ?: "" != editName
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val canEditAge = userRepository.ageFlow.combine(editableAge) { savedAge, editAge ->
        savedAge.toString() != editAge && editAge.toIntOrNull() != null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    init {
        viewModelScope.launch {
            editableName.value = userRepository.nameFlow.first() ?: ""
            editableAge.value = userRepository.ageFlow.first()?.let {
                it.toString()
            } ?: ""
            editableGender.value = userRepository.genderFlow.first()
        }
    }

    fun setName() {
        viewModelScope.launch {
            userRepository.setName(editableName.value)
        }
    }

    fun setAge() {
        editableAge.value.toIntOrNull()?.let {
            viewModelScope.launch {
                userRepository.setAge(it)
            }
        }
    }

    fun setGender() {
        editableGender.value?.let {
            viewModelScope.launch {
                userRepository.setGender(it)
            }
        }
    }
}
