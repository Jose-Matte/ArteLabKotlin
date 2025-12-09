package com.localgo.artelabspa.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.localgo.artelabspa.data.local.SessionManager
import com.localgo.artelabspa.data.repository.AvatarRepositoryBackend

class ProfileViewModelFactory(
    private val application: Application,
    private val avatarRepository: AvatarRepositoryBackend,
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(
                application = application,
                avatarRepository = avatarRepository,
                sessionManager = sessionManager
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
