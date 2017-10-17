package com.levitelservices.levitelservicesdemoapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.levitelservices.levitelservicesdemoapp.R
import com.levitelservices.levitelservicesdemoapp.data.UserRepositorySharedPrefs
import com.levitelservices.levitelservicesdemoapp.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
        initPresenter()
    }

    private fun initPresenter() {
        mainPresenter = MainPresenter(this, UserRepositorySharedPrefs(this))
    }

    private fun initUI() {
        btnLogout.setOnClickListener {
            mainPresenter.handleLogout()
        }
    }

    override fun showUsername(username: String) {
        tvWelcome?.text = getString(R.string.welcome_user_name, username)
    }

    override fun showLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
