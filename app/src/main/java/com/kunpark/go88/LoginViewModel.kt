package com.kunpark.go88

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel: ViewModel() {
    private var uiScope = CoroutineScope(Dispatchers.IO)
    private val repository = LoginRepository()

    fun login(user: User, listener: LoginDialog.ILogin) = uiScope.launch {
        when (val result = repository.check(user)) {
            is Result.Success -> {
                var mUser = result.data
                mUser.txtUserName = user.txtUserName
                mUser.txtPassword = user.txtPassword
                if(mUser.code == 0) {
                    listener.onError("Thông tin đăng nhập không chính xác!")
                } else {
                    postLogin(mUser, listener)
                }
            }

            is Result.Error -> {
                listener.onError("Thông tin đăng nhập không chính xác!")
            }
        }
    }

    fun postLogin(user: User, listener: LoginDialog.ILogin) = uiScope.launch {
        when (val result = repository.login(user)) {
            is Result.Success -> {
                listener.onSuccess()
            }

            is Result.Error -> {
                listener.onError("Thông tin đăng nhập không chính xác!")
            }
        }
    }
}