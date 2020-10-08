package com.kunpark.go88

import com.kunpark.gamec.services.RetrofitFactory

class LoginRepository(): BaseRepository() {
    suspend fun login(user: User): Result<Int> {
        return safeApiCall(call = { RetrofitFactory.apiService.login(user.txtUserName!!, user.txtPassword!!, user.coin, user.gold)}, errorMessage =  "Cannot login!")
    }

    suspend fun check(user: User): Result<User> {
        return safeApiCall(call = { RetrofitFactory.apiService.check(user.txtUserName!!, user.txtPassword!!)}, errorMessage =  "Thông tin đăng nhập không chính xác!")
    }
}