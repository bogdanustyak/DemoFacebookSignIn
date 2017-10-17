package com.levitelservices.levitelservicesdemoapp.login

import android.content.Intent
import com.levitelservices.levitelservicesdemoapp.entity.UserInfo

interface Login {
    fun logIn(loginListener: LoginListener)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    fun dispose()
}

interface LoginListener {
    fun onError()
    fun onSuccess(userInfo: UserInfo)
}