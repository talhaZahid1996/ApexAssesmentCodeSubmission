package com.apex.codeassesment.data

import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUser(forceUpdate: Boolean): Flow<Resource<User>>
    suspend fun getUsers(): Flow<Resource<List<User>>>

}