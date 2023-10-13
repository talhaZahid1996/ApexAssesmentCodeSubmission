package com.apex.codeassesment.data

import com.apex.codeassesment.data.local.LocalDataSource
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.remote.Api
import com.apex.codeassesment.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

// TODO (2 points) : Add tests
// TODO (3 points) : Hide this class through an interface, inject the interface in the clients instead and remove warnings
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val api: Api,
    private val localDataSource: LocalDataSource
) : UserRepository {

    override suspend fun getUser(forceUpdate: Boolean): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading(true))
            if (!forceUpdate) {
                emit(Resource.Success(data = localDataSource.loadUser()))
                return@flow
            } else {

                try {
                    val response = api.getUser()
                    emit(Resource.Success(data = response.mListUser[0]))
                } catch (e: HttpException) {
                    e.printStackTrace()
                    emit(Resource.Error("Couldn't load data"))
                }

            }

        }
    }

    override suspend fun getUsers(): Flow<Resource<List<User>>> {
        return flow {
            emit(Resource.Loading(true))
            val response = try {
                api.getUsers()
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            response?.let {
                emit(Resource.Success(data = it.mListUser))
            }
        }
    }

}
