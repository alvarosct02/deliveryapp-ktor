package com.kamiz.models

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert

data class User(
    val id: Int = -1,
    val firstName: String,
    val lastName: String,
    val email: String,
    val passwordHash: String
)

object UserTable : Table() {
    val id = integer("id").autoIncrement()
    val firstName = varchar("firstName", 128)
    val lastName = varchar("lastName", 128)
    val email = varchar("email", 128)
    val passwordHash = varchar("passwordHash", 128)

     override val primaryKey = PrimaryKey(id)

     fun resultRowToItem(row: ResultRow) = User(
        id = row[id],
        firstName = row[firstName],
        lastName = row[lastName],
        email = row[email],
        passwordHash = row[passwordHash]
    )

     fun itemToResultRow(item: User) = insert {
        it[firstName] = item.firstName
        it[lastName] = item.lastName
        it[email] = item.email
        it[passwordHash] = item.passwordHash
    }
}