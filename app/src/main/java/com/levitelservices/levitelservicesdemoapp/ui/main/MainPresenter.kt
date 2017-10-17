package com.levitelservices.levitelservicesdemoapp.ui.main

import com.levitelservices.levitelservicesdemoapp.data.UserRepository
import java.lang.ref.WeakReference

class MainPresenter(view: MainView, private val userRepository: UserRepository) {

    private val viewRef = WeakReference(view)

    init {
        val userInfo = userRepository.getUser()
        val userName = userInfo?.userName ?: ""
        viewRef.get()?.showUsername(userName)
    }

    fun handleLogout() {
        userRepository.logOut()
        viewRef.get()?.showLoginScreen()
    }
}