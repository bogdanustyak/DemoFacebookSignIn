package com.levitelservices.levitelservicesdemoapp.ui.login.facebook

import android.content.Intent
import com.levitelservices.levitelservicesdemoapp.entity.UserInfo
import com.levitelservices.levitelservicesdemoapp.login.Login
import com.levitelservices.levitelservicesdemoapp.login.LoginListener
import com.levitelservices.levitelservicesdemoapp.ui.login.AuthView
import java.lang.ref.WeakReference


/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class FacebookLoginPresenter(authView: AuthView, private val login: Login) : LoginPresenter,
        LoginListener {

    private val viewRef = WeakReference(authView)

    override fun logIn() {
        login.logIn(this)
    }

    override fun onSuccess(userInfo: UserInfo) {
        viewRef.get()?.goToMainScreen()
    }

    override fun onError() {
        viewRef.get()?.showAuthError()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        login.onActivityResult(requestCode, resultCode, data)
    }

    override fun dispose() {
        login.dispose()
    }
}