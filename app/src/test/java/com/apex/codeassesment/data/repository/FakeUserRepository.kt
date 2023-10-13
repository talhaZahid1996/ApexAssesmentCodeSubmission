package com.apex.codeassesment.data.repository

import com.apex.codeassesment.data.UserRepository
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserRepository : UserRepository {

    private val mListUser = mutableListOf<User>()

    fun addUser(user: User) {
        mListUser.add(user)
    }

    override suspend fun getUser(forceUpdate: Boolean): Flow<Resource<User>> {
        return flow {
            emit(Resource.Success(mListUser[0]))
        }
    }

    override suspend fun getUsers(): Flow<Resource<List<User>>> {
        return flow { emit(Resource.Success(mListUser)) }
    }
}