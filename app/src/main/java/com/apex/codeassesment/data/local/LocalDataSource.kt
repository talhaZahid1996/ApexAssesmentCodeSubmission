package com.apex.codeassesment.data.local

import android.util.Log
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.model.User.Companion.createRandom
import com.squareup.moshi.Moshi
import javax.inject.Inject

// TODO (3 points): Convert to Kotlin
// TODO (2 point): Add tests
// TODO (1 point): Use the correct naming conventions.
// TODO (3 points): Inject all dependencies instead of instantiating them.
// TODO (1 point): Address Android Studio warning
class LocalDataSource @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val moshi: Moshi
) {

    fun loadUser(): User {
        val serializedUser = preferencesManager.loadUser()
        val jsonAdapter = moshi.adapter(User::class.java)
        return createRandom()
        /*return try {
            if (serializedUser.isNullOrBlank()) {
                createRandom()
            } else {
                jsonAdapter.fromJson(serializedUser) ?: createRandom()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            createRandom()
        }*/
    }

    fun saveUser(user: User) {
        val jsonAdapter = moshi.adapter(
            User::class.java
        )
        val serializedUser = jsonAdapter.toJson(user)
        preferencesManager.saveUser(serializedUser)
    }
}