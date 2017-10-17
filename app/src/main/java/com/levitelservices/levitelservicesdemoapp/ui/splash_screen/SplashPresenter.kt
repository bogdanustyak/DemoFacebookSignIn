package com.levitelservices.levitelservicesdemoapp.ui.splash_screen

import android.os.Handler
import com.levitelservices.levitelservicesdemoapp.data.UserRepository
import java.lang.ref.WeakReference

class SplashPresenter(view: SplashScreenView, private val userRepository: UserRepository) {

    private val viewRef = WeakReference(view)
    private val handler = Handler()
    private val splashRunnable = { checkAuth() }

    init {
        handler.postDelayed(splashRunnable, SPLASH_TIME_OUT)
    }

    private fun checkAuth() {
        if (userRepository.isLoggedIn()) {
            viewRef.get()?.goToMainScreen()
        } else {
            viewRef.get()?.goToLoginScreen()
        }
    }

    fun dispose() {
        handler.removeCallbacksAndMessages(null)
    }

    companion object {
        /**
         * the number of milliseconds to wait before going to login screen.
         */
        private val SPLASH_TIME_OUT = 3000L
    }
}