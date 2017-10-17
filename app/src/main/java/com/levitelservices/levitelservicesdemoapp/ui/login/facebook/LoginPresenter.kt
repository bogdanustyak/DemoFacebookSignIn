package com.levitelservices.levitelservicesdemoapp.ui.login.facebook

import android.content.Intent


/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
interface LoginPresenter {
    fun logIn()
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    fun dispose()
}