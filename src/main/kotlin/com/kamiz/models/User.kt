package com.kamiz.models

import org.jetbrains.exposed.sql.Table

data class User(
    val id: Int = -1,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)


object Users : Table() {
    val id = integer("id").autoIncrement()
    val firstName = varchar("firstName", 128)
    val lastName = varchar("lastName", 128)
    val email = varchar("email", 128)
    val password = varchar("password", 128)

    override val primaryKey = PrimaryKey(id)
}