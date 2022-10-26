package com.kamiz.plugins

import com.kamiz.database.DatabaseFactory
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {

    routing {
        get("/") {
            val users = DatabaseFactory.userDao.all()
            call.respondText("Users: ${users.size}")
        }

    }
}
