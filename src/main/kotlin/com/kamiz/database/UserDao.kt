package com.kamiz.database

import com.kamiz.database.DatabaseFactory.dbQuery
import com.kamiz.models.User
import com.kamiz.models.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement

abstract class BaseDao<T>() {
    abstract fun resultRowToItem(row: ResultRow): T
    abstract fun itemToResultRow(item: T): InsertStatement<Number>

    abstract suspend fun all(): List<T>
    abstract suspend fun getById(id: Int): T?
    abstract suspend fun add(item: T): T?
    abstract suspend fun edit(item: T): T?
    abstract suspend fun delete(id: Int): Boolean
}

class UserDao : BaseDao<User>() {

    override fun resultRowToItem(row: ResultRow) = User(
        id = row[Users.id],
        firstName = row[Users.firstName],
        lastName = row[Users.lastName],
        email = row[Users.email],
        password = row[Users.password]
    )

    override fun itemToResultRow(item: User) = Users.insert {
        it[firstName] = item.firstName
        it[lastName] = item.lastName
        it[email] = item.email
        it[password] = item.password
    }

    override suspend fun all(): List<User> = dbQuery {
        Users.selectAll().map(::resultRowToItem)
    }

    override suspend fun getById(id: Int): User? {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun edit(item: User): User {
        TODO("Not yet implemented")
    }

    override suspend fun add(item: User): User? = dbQuery {
        itemToResultRow(item).resultedValues?.singleOrNull()?.let(::resultRowToItem)
    }
}
