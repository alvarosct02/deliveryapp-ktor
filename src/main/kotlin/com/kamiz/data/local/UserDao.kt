package com.kamiz.data.local

import com.kamiz.data.local.DatabaseFactory.dbQuery
import com.kamiz.models.User
import com.kamiz.models.UserTable
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

abstract class BaseDao<T>() {

    abstract suspend fun all(): List<T>
    abstract suspend fun getById(id: Int): T?
    abstract suspend fun add(item: T): T?
    abstract suspend fun edit(item: T): T?
    abstract suspend fun delete(id: Int): Boolean
}

class UserDao : BaseDao<User>() {

    override suspend fun all(): List<User> = dbQuery {
        UserTable.selectAll().map(UserTable::resultRowToItem)
    }

    override suspend fun getById(id: Int): User? {
        TODO("Not yet implemented")
    }

    suspend fun findByEmail(email: String): User? = dbQuery {
        UserTable.select { (UserTable.email eq email) }
            .map(UserTable::resultRowToItem)
            .firstOrNull()
    }

    override suspend fun delete(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun edit(item: User): User {
        TODO("Not yet implemented")
    }

    override suspend fun add(item: User): User? = dbQuery {
        UserTable.itemToResultRow(item).resultedValues?.singleOrNull()?.let(UserTable::resultRowToItem)
    }
}
