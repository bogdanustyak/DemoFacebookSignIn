package com.levitelservices.levitelservicesdemoapp.data

import com.levitelservices.levitelservicesdemoapp.entity.UserInfo

interface UserRepository {
    fun save(user: UserInfo)
    fun getUser(): UserInfo?
    fun logOut()
    fun isLoggedIn(): Boolean
}