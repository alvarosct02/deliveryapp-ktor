package com.kamiz.data.local

import com.kamiz.models.User
import com.kamiz.models.UserTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:./build/db3"
        val database = Database.connect(jdbcURL, driverClassName)
        transaction(database) {
            SchemaUtils.create(UserTable)
            seedDb()
        }
    }

    private fun seedDb() {
        userDao.apply {
            runBlocking {
                if (all().isEmpty()) {
                    add(User(-1, "Admin", "Admin", "admin", "045342a1353736ebd40791e91c0e97502fec589e"))
                    add(User(-1, "Admin", "Admin", "maryam", "296edbed74404a3b6045cf8c50732ae6da330829"))
                }
            }
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    val userDao by lazy { UserDao() }
}