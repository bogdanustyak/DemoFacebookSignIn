package com.levitelservices.levitelservicesdemoapp.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.levitelservices.levitelservicesdemoapp.data.UserRepository
import com.levitelservices.levitelservicesdemoapp.entity.UserInfo
import org.json.JSONException
import org.json.JSONObject

class FacebookLogin(private val activity: Activity,
                    private val userRepository: UserRepository) : Login {

    private val callbackManager: CallbackManager = CallbackManager.Factory.create()
    private var listener: LoginListener? = null

    init {
        LoginManager.getInstance().registerCallback(
                callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onError(error: FacebookException?) {
                        listener?.onError()
                    }

                    override fun onCancel() {
                    }

                    override fun onSuccess(result: LoginResult?) {
                        requestUserInfo(result)
                    }
                }
        )
    }

    override fun logIn(loginListener: LoginListener) {
        this.listener = loginListener
        LoginManager.getInstance().logInWithReadPermissions(activity,
                listOf(FB_EMAIL_PERMISSION, FB_PUBLIC_PROFILE_PERMISSION))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (FacebookSdk.isFacebookRequestCode(requestCode)) {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun dispose() {
        listener = null
    }

    private fun requestUserInfo(result: LoginResult?) {
        val request = GraphRequest.newMeRequest(
                result?.accessToken
        ) { jsonResp, _ ->
            try {
                onLoginSuccess(jsonResp, result)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        val parameters = Bundle()
        parameters.putString(KEY_FIELDS, "id,name,email,gender,birthday")
        request.parameters = parameters
        request.executeAsync()
    }

    private fun onLoginSuccess(jsonResp: JSONObject, result: LoginResult?) {
        val name = jsonResp.getString(KEY_NAME)
        val email = jsonResp.getString(KEY_EMAIL)
        val accessToken = result?.accessToken?.token ?: ""
        val userInfo = UserInfo(accessToken, email, name)

        if (userRepository.isLoggedIn() && accessToken.isNotEmpty()) {
            userRepository.save(userInfo)
            listener?.onSuccess(userInfo)
        }
    }

    companion object {
        private val FB_EMAIL_PERMISSION = "email"
        private val FB_PUBLIC_PROFILE_PERMISSION = "public_profile"

        val READ_PERMISSIONS = listOf(FB_PUBLIC_PROFILE_PERMISSION, FB_EMAIL_PERMISSION)

        private val KEY_NAME = "name"
        private val KEY_EMAIL = "email"
        private val KEY_FIELDS = "fields"

    }
}