package com.code.kembang_telon.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[ID_KEY] ?: "",
                preferences[NAME_KEY] ?:"",
                preferences[EMAIL_KEY] ?:"",
                preferences[ALAMAT_KEY] ?: "",
                preferences[USERNAME_KEY] ?:"",
                preferences[PHONENUMBER_KEY] ?:"",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveUser(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = user.id
            preferences[NAME_KEY] = user.name
            preferences[EMAIL_KEY] = user.email
            preferences[ALAMAT_KEY] = user.alamat
            preferences[PHONENUMBER_KEY] = user.phoneNumber ?: ""
            preferences[STATE_KEY] = user.isLogin

        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.remove(ID_KEY)
            preferences.remove(NAME_KEY)
            preferences.remove(EMAIL_KEY)
            preferences.remove(ALAMAT_KEY)
            preferences.remove(USERNAME_KEY)
            preferences.remove(PHONENUMBER_KEY)
            preferences.remove(STATE_KEY)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null
        private val ID_KEY = stringPreferencesKey("id")
        private val NAME_KEY = stringPreferencesKey("name")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val ALAMAT_KEY = stringPreferencesKey("alamat")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val PHONENUMBER_KEY = stringPreferencesKey("phone_number")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}