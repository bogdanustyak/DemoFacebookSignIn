package com.levitelservices.levitelservicesdemoapp.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.gson.Gson
import com.levitelservices.levitelservicesdemoapp.entity.UserInfo


/**
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
class UserRepositorySharedPrefs(val context: Context) : UserRepository {

    /**
     * Save user to shared prefs
     * @param user to save in shared prefs
     */
    override fun save(user: UserInfo) {
        val editor = context.getSharedPreferences(USER_REPO_PREFS, MODE_PRIVATE).edit()
        editor.putString(KEY_USER, Gson().toJson(user))
        editor.apply()
    }

    /**
     * retrieves user from shared prefs
     * @return UserInfo
     */
    override fun getUser(): UserInfo? {
        val userInfo: UserInfo?
        val prefs = context.getSharedPreferences(USER_REPO_PREFS, MODE_PRIVATE)
        userInfo = if (prefs.contains(KEY_USER)) {
            Gson().fromJson<UserInfo>(
                    prefs.getString(KEY_USER, ""),
                    UserInfo::class.java
            )
        } else {
            null
        }
        return userInfo
    }

    /**
     * make a fb logout and clears shared prefs
     */
    override fun logOut() {
        val editor = context.getSharedPreferences(USER_REPO_PREFS, MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
        LoginManager.getInstance().logOut()
    }

    /**
     * check if user is signed in
     * @return true if user is signed in, false otherwise
     */
    override fun isLoggedIn(): Boolean {
        return isFBUserLoggedIn()
    }

    /**
     * check if user is signed in into Facebook
     * @return true if user's access token exists, false otherwise
     */
    private fun isFBUserLoggedIn(): Boolean {
        return AccessToken.getCurrentAccessToken() != null
    }

    companion object {
        private val USER_REPO_PREFS = "user_repo_prefs"
        private val KEY_USER = "user_info"
    }
}