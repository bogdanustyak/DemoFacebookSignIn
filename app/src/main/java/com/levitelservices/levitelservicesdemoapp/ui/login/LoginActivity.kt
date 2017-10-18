package com.levitelservices.levitelservicesdemoapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.levitelservices.levitelservicesdemoapp.R
import com.levitelservices.levitelservicesdemoapp.data.UserRepositorySharedPrefs
import com.levitelservices.levitelservicesdemoapp.login.FacebookLogin
import com.levitelservices.levitelservicesdemoapp.ui.main.MainActivity
import com.levitelservices.levitelservicesdemoapp.ui.login.facebook.FacebookLoginPresenter
import com.levitelservices.levitelservicesdemoapp.ui.login.facebook.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthView {

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initUI()
        initPresenter()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        presenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun goToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showAuthError() {
        Toast.makeText(this, getString(R.string.login_error_default_message),
                Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.dispose()
        super.onDestroy()
    }

    private fun initUI() {
        fbLoginBtn.setOnClickListener {
            presenter.logIn()
        }
    }

    private fun initPresenter() {
        val userRepository = UserRepositorySharedPrefs(this)
        val facebookLogin = FacebookLogin(this, userRepository)

        presenter = FacebookLoginPresenter(this, facebookLogin)
        fbLoginBtn.setReadPermissions(FacebookLogin.READ_PERMISSIONS)
    }
}
