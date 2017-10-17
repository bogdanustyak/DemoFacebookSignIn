package com.levitelservices.levitelservicesdemoapp.ui.splash_screen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.levitelservices.levitelservicesdemoapp.R
import com.levitelservices.levitelservicesdemoapp.data.UserRepositorySharedPrefs
import com.levitelservices.levitelservicesdemoapp.ui.main.MainActivity
import com.levitelservices.levitelservicesdemoapp.ui.login.LoginActivity

/**
 * The application Splash Screen
 * Will be shown for a 3 seconds and then will be closed by opening Login screen
 */
class SplashActivity : AppCompatActivity(), SplashScreenView {

    private lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initPresenter()
    }

    private fun initPresenter() {
        presenter = SplashPresenter(this, UserRepositorySharedPrefs(this))
    }

    override fun goToLoginScreen() {
        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun goToMainScreen() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Clearing resources on destroy
     */
    override fun onDestroy() {
        presenter.dispose()
        super.onDestroy()
    }
}
