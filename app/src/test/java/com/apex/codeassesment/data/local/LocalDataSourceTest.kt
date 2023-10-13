package com.apex.codeassesment.data.local

import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.repository.FakeUserRepository
import com.apex.codeassesment.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LocalDataSourceTest {

    private lateinit var userRepository: FakeUserRepository
    private val user = User.createRandom()

    @Before
    fun setup() {
        userRepository = FakeUserRepository()
        userRepository.addUser(user)
    }

    @Test
    fun `Get User`() {
        runBlocking {
            userRepository.getUser(false).onEach {
                when (it) {
                    is Resource.Loading -> {}
                    is Resource.Error -> {}

                    is Resource.Success -> {
                        assertThat(it).isEqualTo(user)
                    }
                }
            }
        }
    }

    @Test
    fun saveUser() {
        runBlocking {
            userRepository.addUser(user)
            userRepository.getUser(false).onEach {
                when (it) {
                    is Resource.Loading -> {}
                    is Resource.Error -> {}

                    is Resource.Success -> {
                        assertThat(it).isEqualTo(user)
                    }
                }
            }
        }
    }
}