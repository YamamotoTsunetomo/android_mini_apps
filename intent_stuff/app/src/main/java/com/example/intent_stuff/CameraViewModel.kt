package com.example.intent_stuff

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow


data class Event<T>(val content: T) {
    fun hasBeenHandled() = content == null
}

class CameraViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val event: MutableLiveData<Event<Uri?>> = MutableLiveData(
        Event(savedStateHandle.get<Uri?>(USER_UI_STATE_KEY))
    )


    fun setSavedStateHandle(uri: Uri?) {
        this.event.value = Event(uri)
        savedStateHandle.set(USER_UI_STATE_KEY, uri)
    }

    companion object {
        const val USER_UI_STATE_KEY = "user_ui_state"
    }
}