package com.apex.codeassesment.data.remote

import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.remote.dto.UserResponseDto
import okhttp3.ResponseBody
import retrofit2.http.GET

interface Api {
    @GET("api")
    suspend fun getUser(): UserResponseDto

    @GET("api?results=10")
    suspend fun getUsers(): List<User>

    companion object {
        const val BASE_URL = "https://randomuser.me/"
    }

}