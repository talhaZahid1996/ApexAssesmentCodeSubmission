package com.apex.codeassesment.data.remote.dto

import com.apex.codeassesment.data.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponseDto(
    @field:Json(name="results") val mListUser: MutableList<User>
)
