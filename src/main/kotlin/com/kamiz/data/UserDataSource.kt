package com.kamiz.data

import com.kamiz.data.local.DatabaseFactory
import com.kamiz.models.User

class UserDataSource() {
    suspend fun getUserByUsername(username: String): User? {
        return DatabaseFactory.userDao.findByEmail(username)
    }
}
