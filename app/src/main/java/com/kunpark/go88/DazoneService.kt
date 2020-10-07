package com.kunpark.go88
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface DazoneService {

    @POST(Config.LOGIN)
    suspend fun login(@Body param: JsonObject): Response<Int>

    @FormUrlEncoded
    @POST(Config.CHECK)
    suspend fun check(@Body param: JsonObject): Response<User>
}
